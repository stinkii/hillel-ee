package hillelee.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final JpaPetRepository petRepository;


    public List<Pet> getPetsUsingSeparateJpaMethods(Optional<String> specie, Optional<Integer> age) {

        if(specie.isPresent() && age.isPresent()){
            return petRepository.findBySpecieAndAge(specie.get(),age.get());
        }
        if (specie.isPresent()){
            return petRepository.findBySpecie(specie.get());
        }
        if(age.isPresent()){
            return petRepository.findByAge(age.get());
        }

        return petRepository.findAll();
    }

    private List<Pet> getPetUsingStreamFilters(Optional<String> specie, Optional<Integer> age){
        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = specieFilter.and(ageFilter);

        return petRepository.findAll().stream().filter(complexFilter).collect(Collectors.toList());
    }

    public List<Pet> getPetUsingSingleJpaMethod(Optional<String> specie, Optional<Integer> age){
        return petRepository.findNullableBySpecieAndAge(specie.orElse(null),age.orElse(null));
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
}
