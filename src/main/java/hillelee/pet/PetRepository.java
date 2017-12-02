package hillelee.pet;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PetRepository {
    private Map<Integer, Pet> pets = new ConcurrentHashMap<Integer, Pet>() {{
        put(0, new Pet(0, "Tom", "Cat", 3));
        put(1, new Pet(1, "Jerry", "Mouse", 1));
    }};

    private AtomicInteger counter = new AtomicInteger(1);

    public Collection<Pet> findAll() {
        return pets.values();
    }

    public Optional<Pet> findById(Integer id) {
        return Optional.ofNullable(pets.get(id));
    }

    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(counter.incrementAndGet());
        }
        pets.put(pet.getId(), pet);

        return pet;
    }

    public Optional<Pet> delete(Integer id) {
        return Optional.ofNullable(pets.remove(id));
    }
}
