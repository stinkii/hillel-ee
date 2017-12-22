package hillelee.doctor;


import hillelee.pet.ErrorBody;
import hillelee.util.DoctorConfig;
import hillelee.util.NoSuchDoctorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorConfig config;

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> findDoctorById(@PathVariable Integer id) {
        Optional<Doctor> maybeDoctor = doctorService.getById(id);

        return maybeDoctor.map(Object.class::cast)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no doctor with ID " + id)));

    }


    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<String> name,
                                   @RequestParam Optional<List<String>> specializations) {

        List<Doctor> doctors = doctorService.getDoctors(specializations, name);
        return doctors;
    }

    @PostMapping("/doctors")
    public ResponseEntity<Void> createADoctor(@RequestBody Doctor doctor) {
        if (config.getSpecializations().containsAll(doctor.getSpecializations())) {
            Doctor saved = doctorService.save(doctor);
            return ResponseEntity.created(URI.create("/doctors/" + saved.getId())).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> updateADoctor(@RequestBody Doctor doctor,
                                           @PathVariable Integer id) {
        if (doctorService.doctorExists(id)) {
            doctor.setId(id);
            Doctor temp = new Doctor();
            temp.setId(doctor.getId());
            temp.setName(doctor.getName());
            temp.setSpecializations(doctor.getSpecializations());
            temp.setAppointments(doctor.getAppointments());
            doctorService.save(temp);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteADoctor(@PathVariable Integer id) {
        doctorService.delete(id).orElseThrow(NoSuchDoctorException::new);
    }

    @GetMapping("/doctors/specializations")
    public List<String> getAllSpecializations() {

        return config.getSpecializations();
        /*return Arrays.stream(Specializations.values()).map(specializations -> specializations.toString())
                .collect(Collectors.toList());*/
    }

    @GetMapping("/doctors/{id}/schedule/{date}")
    public Set<Appointment> getSchedule(@PathVariable Integer id,
                                        @PathVariable Optional<String> date) {
        return doctorService.getDoctorsSchedule(id, date);
    }

}
