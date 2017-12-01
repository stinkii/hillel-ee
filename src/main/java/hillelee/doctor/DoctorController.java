package hillelee.doctor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class DoctorController {
    private List<Doctor> doctors = new ArrayList<Doctor>() {{

    }};


    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> findDoctorById(@PathVariable Integer id) {
        return  doctors.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .map(d -> ResponseEntity.ok(d))
                .orElseGet( () -> ResponseEntity.notFound().build());
    }


    @GetMapping("/doctors")
    public List<Doctor> findDoctorBySpecOrName(@RequestParam Optional<String> spec,
                                         @RequestParam Optional<String> name) {
        Predicate<Doctor> specFilter = spec.map(this::filterBySpecialization)
                .orElse(pet -> true);

        Predicate<Doctor> nameFilter = name.map(this::filterByName)
                .orElse(pet -> true);

        Predicate<Doctor> complexFilter = specFilter.and(nameFilter);

        return doctors.stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    private Predicate<Doctor> filterBySpecialization(String spec){
        return doctor -> doctor.getSpecialization().equals(spec);
    }
    private Predicate<Doctor> filterByName(String name){
        return doctor -> doctor.getName().startsWith(name);
    }


    @PostMapping("/doctors")
    public ResponseEntity<Void> createADoctor(@RequestBody Doctor doctor) {
        if(doctors.stream().filter(doctor1 -> doctor1.getId().equals(doctor.getId())).count()==0){
            Doctor doc = new Doctor();
            doc.setId(AutoIncrement.doIncrement());
            doc.setName(doctor.getName());
            doc.setSpecialization(doctor.getSpecialization());
            doctors.add(doc);
            return ResponseEntity.created(URI.create("/doctors/" + AutoIncrement.getValue())).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<Void> updateADoctor(@RequestBody Doctor doctor,
                                              @PathVariable Integer id) {
        Optional<Doctor> d = doctors.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst();

        Doctor temp;
        if (d.isPresent()) {
            temp = d.get();
            temp.setName(doctor.getName());
            temp.setSpecialization(doctor.getSpecialization());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/doctors/{id}")
    public void deleteADoctor(@PathVariable Integer id) {
        Doctor d = doctors.stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst().get();
        doctors.remove(d);
    }
}
