package hillelee.doctor;


import hillelee.doctor.dto.PetIdInputDto;
import hillelee.pet.ErrorBody;
import hillelee.store.NoSuchMedicineException;
import hillelee.util.DoctorConfig;
import hillelee.util.NoSuchDoctorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
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

    @PostMapping("/doctors/{id}/schedule/{date}/{time}")
    public ResponseEntity<Void> createAnAppointment(@PathVariable Integer id,
                                                    @PathVariable String date,
                                                    @PathVariable String time,
                                                    @RequestBody PetIdInputDto petId) {
        Doctor saved = doctorService.createAnAppointment(id, LocalDate.parse(date),
                LocalTime.parse(time)/*.of(Integer.valueOf(time), 0)*/, petId.getPetId());
        return ResponseEntity.created(
                URI.create("/doctors/" + saved.getId())).build();

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
    public Map<LocalTime, Integer> getSchedule(@PathVariable Integer id,
                                               @PathVariable String date) {
        return doctorService.getDoctorsSchedule(id, LocalDate.parse(date));
    }


    @ExceptionHandler(NoSuchDoctorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void nosuchdoctor() {
    }

}
