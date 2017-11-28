package hillelee.doctor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DoctorController {
    private List<Doctor> doctors = new ArrayList<Doctor>() {{

    }};

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() {

        return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> findDoctorById(@PathVariable Integer id) {
        Optional<Doctor> d = doctors.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst();
        Doctor temp;
        if (d.isPresent()) {
            temp = d.get();
            return ResponseEntity.ok(temp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /*
    @GetMapping("/doctors")
    public Doctor findDoctorBySpec(@RequestParam String spec) {
        return doctors;
    }

    @GetMapping("/doctors")
    public Doctor findDoctorBySpec(@RequestParam String name) {
        return doctors;
    }*/

    @PostMapping("/doctors")
    public ResponseEntity<Void> createADoctor(@RequestBody Doctor doctor) {
        Doctor doc = new Doctor();
        doc.setId(AutoIncrement.doIncrement());
        doc.setName(doctor.getName());
        doc.setSpecialization(doctor.getSpecialization());
        doctors.add(doc);
        return ResponseEntity.created(URI.create("/doctors/" + AutoIncrement.getValue())).build();
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
