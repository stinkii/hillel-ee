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

    private final PetRepository petRepository;


    public List<Pet> getPets(Optional<String> specie,
                             Optional<Integer> age) {
        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = specieFilter.and(ageFilter);

        return petRepository.findAll().stream().filter(complexFilter).collect(Collectors.toList());
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

        return petRepository.delete(id);
    }
}
