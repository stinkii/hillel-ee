package hillelee.doctor;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DoctorRepository {
    private List<Doctor> doctors = Collections.synchronizedList(new ArrayList<Doctor>());

    private AtomicInteger counter = new AtomicInteger(0);

    public Collection<Doctor> findAll() {
        return doctors;
    }

    public Optional<Doctor> findById(Integer id) {
        return Optional.ofNullable(doctors.get(id));
    }

    public Doctor save(Doctor doctor) {
        if (doctor.getId() == null) {
            doctor.setId(counter.incrementAndGet());
        }
        doctors.add(doctor);

        return doctor;
    }

    public Optional<Doctor> delete(Integer id) {

        Doctor doc = doctors.stream().filter(doctor -> doctor.getId().equals(id)).findFirst().get();

        return Optional.ofNullable(doctors.remove(doc.getId().intValue()));
    }
}
