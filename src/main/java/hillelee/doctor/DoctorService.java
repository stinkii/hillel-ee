package hillelee.doctor;


import hillelee.pet.Pet;
import hillelee.util.EngagedAppointmentException;
import hillelee.util.InvalidDateException;
import hillelee.util.NoSuchDoctorException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final JpaDoctorRepository doctorRepository;

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

    public Map<LocalTime, Integer> getDoctorsSchedule(Integer id, LocalDate date) {
        if (!doctorRepository.findById(id).isPresent()) {
            throw new NoSuchDoctorException();
        }

        Map<LocalTime, Integer> map = doctorRepository.findById(id)
                .get()
                .getAppointments().stream()
                .filter(appointment -> appointment.getComingDate().equals(date))
                .collect(Collectors.toMap(Appointment::getTime, Appointment::getPetId, (a, b) -> b));

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    }

    public Doctor createAnAppointment(Integer id, LocalDate date, LocalTime time, Integer petId) {
        if (!doctorRepository.findById(id).isPresent()) {
            throw new NoSuchDoctorException();
        }
        Doctor doctor = doctorRepository.findById(id).get();
        Appointment appointment = new Appointment(date, time, petId);
        if (appointment.getComingDate().isBefore(LocalDate.now()) ||
                appointment.getTime().isBefore(LocalTime.of(8, 0)) ||
                appointment.getTime().isAfter(LocalTime.of(16, 0)) ||
                appointment.getTime().getMinute() != 0) {
            throw new InvalidDateException();
        } else if (doctor.getAppointments().
                stream()
                .anyMatch(appointment1 -> appointment1.getComingDate().equals(date) &&
                        appointment1.getTime().equals(time))) {
            throw new EngagedAppointmentException();
        }
        doctor.getAppointments().add(appointment);
        return doctorRepository.save(doctor);
    }

}
