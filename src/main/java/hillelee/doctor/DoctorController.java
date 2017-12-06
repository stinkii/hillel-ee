package hillelee.doctor;


import hillelee.pet.ErrorBody;
import hillelee.util.NoSuchDoctorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> findDoctorById(@PathVariable Integer id) {
        Optional<Doctor> maybeDoctor = doctorService.getById(id);

        return maybeDoctor.map(Object.class::cast)
                .map(doc -> ResponseEntity.ok(doc))
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no doctor with ID " + id)));
    }


    @GetMapping("/doctors")
    public List<Doctor> findDoctorBySpecOrName(@RequestParam Optional<String> spec,
                                               @RequestParam Optional<String> name) {

        return doctorService.getDoctors(spec, name);
    }

    @PostMapping("/doctors")
    public ResponseEntity<Void> createADoctor(@RequestBody Doctor doctor) {
        Doctor saved = doctorService.save(doctor);
        return ResponseEntity.created(URI.create("/doctors/" + saved.getId())).build();
    }

    @PutMapping("/doctors/{id}")
    public void updateADoctor(@RequestBody Doctor doctor,
                              @PathVariable Integer id) {
        doctor.setId(id);
        doctorService.save(doctor);
    }


    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteADoctor(@PathVariable Integer id) {
        doctorService.delete(id).orElseThrow(NoSuchDoctorException::new);
    }

}
