package hillelee.doctor;


import hillelee.pet.Pet;
import hillelee.util.EngagedAppointmentException;
import hillelee.util.InvalidDateException;
import hillelee.util.NoSuchDoctorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final JpaDoctorRepository doctorRepository;
    private final JpaAppointmentRepository appointmantsRepository;

    public List<Doctor> getDoctors(Optional<List<String>> specializations,
                                   Optional<String> name) {
        if (specializations.isPresent() && name.isPresent()) {
            return doctorRepository.findBySpecializationsInAndName(specializations.get(), name.get());
        }
        if (specializations.isPresent()) {
            return doctorRepository.findBySpecializationsIgnoreCaseIn(specializations
                    .get());
        }
        if (name.isPresent()) {
            return doctorRepository.findByNameIgnoreCase(name.get());
        }
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getById(Integer id) {

        return doctorRepository.findById(id);
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);

    }

    public Optional<Doctor> delete(Integer id) {
        Optional<Doctor> maybeDoctor = doctorRepository.findById(id);

        maybeDoctor.ifPresent(doctor -> doctorRepository.delete(doctor.getId()));

        return maybeDoctor;
    }


    public Boolean doctorExists(Integer id) {
        return doctorRepository.findAll().stream().anyMatch(doc -> doc.getId().equals(id));
    }

    public Set<Appointment> getDoctorsSchedule(Integer id, Optional<String> date) {
        if (!doctorRepository.findById(id).isPresent()) {
            throw new NoSuchDoctorException();
        }
        if (date.isPresent()) {
            return doctorRepository.findById(id)
                    .get().getAppointments().stream()
                    .filter(appointment -> appointment.getComingDate().
                            equals(LocalDate.parse(date.get())))
                    .collect(Collectors.toSet());
        }
        return doctorRepository.findById(id).get().getAppointments();
    }

    public Doctor createAnAppointment(Integer id, String date, String time, Integer petId) {
        if (!doctorRepository.findById(id).isPresent()) {
            throw new NoSuchDoctorException();
        }
        Doctor doctor = doctorRepository.findById(id).get();
        Appointment appointment = new Appointment(LocalDate.parse(date), LocalTime.of(Integer.valueOf(time), 0), petId);
        if (appointment.getComingDate().isBefore(LocalDate.now()) ||
                appointment.getTime().isBefore(LocalTime.of(8, 0)) ||
                appointment.getTime().isAfter(LocalTime.of(16, 0))) {
            throw new InvalidDateException();
        } else if (doctor.getAppointments().
                stream()
                .anyMatch(appointment1 -> appointment1.getComingDate().equals(LocalDate.parse(date)) &&
                        appointment1.getTime().equals(LocalTime.of(Integer.valueOf(time), 0)))) {
            throw new EngagedAppointmentException();
        }
        doctor.getAppointments().add(appointment);
        return doctorRepository.save(doctor);
    }

}
