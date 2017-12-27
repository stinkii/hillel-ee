package hillelee.pet;

import hillelee.pet.dto.PrescriptionInputDto;
import hillelee.store.NoSuchMedicineException;
import hillelee.util.NoSuchPetException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
public class PetController {


    private final PetService petService;

    @GetMapping(value = "/pets")
    public List<Pet> getPets(@RequestParam Optional<String> specie,
                             @RequestParam Optional<Integer> age) {

        List<Pet> pets = petService.getPetUsingSingleJpaMethod(specie, age);
        return pets;
    }

    @GetMapping(value = "/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        Optional<Pet> maybePet = petService.getById(id);

        return maybePet.map(Object.class::cast)
                .map(pet -> ResponseEntity.ok(pet))
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no pet with ID " + id)));
    }

    @PostMapping(value = "/pets")
    public ResponseEntity<Void> createPet(@RequestBody Pet pet) {

        Pet saved = petService.save(pet);

        return ResponseEntity.created(URI.create("/pets/" + saved.getId())).build();
    }

    @PutMapping("/pets/{id}")
    public void updatePet(@PathVariable Integer id,
                          @RequestBody Pet pet) {

        pet.setId(id);
        petService.save(pet);
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id) {

        petService.delete(id).orElseThrow(NoSuchPetException::new);

    }

    @PostMapping("/pets/{id}/prescriptions")
    public void prescribe(@PathVariable Integer id,
                          @RequestBody PrescriptionInputDto dto) {
        petService.prescribe(id, dto.getDescription(),
                dto.getMedicineName(),
                dto.getQuantity(),
                dto.getTimesPerDay());
    }

    @ExceptionHandler(NoSuchMedicineException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void nosuchmedicine() {
    }

}







