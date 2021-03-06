package hillelee.pet;

import hillelee.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final JpaPetRepository petRepository;
    private final StoreService storeService;


    public Page<Pet> getPetsUsingSeparateJpaMethods(Optional<String> specie,
                                                    Optional<Integer> age,
                                                    Optional<LocalDate> birthDate,
                                                    Pageable pageable) {

        if (specie.isPresent() && age.isPresent() && birthDate.isPresent()) {
            return petRepository.findBySpecieAndAgeAndBirthDate(specie.get(), age.get(), birthDate.get(), pageable);
        }
        if (specie.isPresent()) {
            return petRepository.findBySpecie(specie.get(), pageable);
        }
        if (age.isPresent()) {
            return petRepository.findByAge(age.get(), pageable);
        }
        if (birthDate.isPresent()) {
            return petRepository.findByBirthDate(birthDate.get(), pageable);
        }

        return petRepository.findAll(pageable);
    }

    private List<Pet> getPetUsingStreamFilters(Optional<String> specie, Optional<Integer> age) {
        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = specieFilter.and(ageFilter);

        return petRepository.findAll().stream().filter(complexFilter).collect(Collectors.toList());
    }

    @Transactional
    public List<Pet> getPetUsingSingleJpaMethod(Optional<String> specie, Optional<Integer> age) {
        List<Pet> pets = petRepository.findNullableBySpecieAndAge(specie.orElse(null), age.orElse(null));

        pets.forEach(pet -> System.out.println(pet.getPrescriptions()));

        return pets;
    }

    private Predicate<Pet> filterByAge(Integer age) {

        return pet -> pet.getAge().equals(age);
    }

    private Predicate<Pet> filterBySpecie(String specie) {

        return pet -> pet.getSpecie().equals(specie);
    }

    public Optional<Pet> getById(Integer id) {
        return petRepository.findById(id);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> delete(Integer id) {
        Optional<Pet> maybePet = petRepository.findById(id);

        maybePet.ifPresent(pet -> petRepository.delete(pet.getId()));

        /* maybePet.map(Pet::getId).ifPresent(petRepository::delete);*/
        return maybePet;
    }

    @Transactional
    @Retryable(ObjectOptimisticLockingFailureException.class)
    public void prescribe(Integer petId,
                          String description,
                          String medicineName,
                          Integer quantity,
                          Integer timesPerDay) {
        Pet pet = petRepository.findById(petId).orElseThrow(RuntimeException::new);

        pet.getPrescriptions().add(new Prescription(description, LocalDate.now(), timesPerDay, MedicineType.PERORAL));

        petRepository.save(pet);

        storeService.decrement(medicineName, quantity);
    }
}
