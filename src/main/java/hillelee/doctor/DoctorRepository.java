package hillelee.doctor;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DoctorRepository {
    private List<Doctor> doctors = Collections.synchronizedList(new ArrayList<Doctor>());


    public Collection<Doctor> findAll() {
        return doctors;
    }

    public Optional<Doctor> findById(String id) {
        return doctors.stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst();
    }

    public Doctor save(Doctor doctor) {
        if (doctor.getId().isEmpty()) {
            doctor.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            doctors.add(doctor);
        } else {
            Doctor temp = doctors.stream()
                    .filter(doc -> doc.getId().equals(doctor.getId()))
                    .findFirst().get();
            int index = doctors.indexOf(temp);

            doctors.set(index, doctor);
        }
        return doctor;
    }

    public Optional<Doctor> delete(String id) {

        Optional<Doctor> doc = doctors.stream().filter(doctor -> doctor.getId().equals(id)).findFirst();

        if(doc.isPresent()) {
            return Optional.ofNullable(doctors.remove(doctors.indexOf(doc)));
        }
        return doc;
    }
}
