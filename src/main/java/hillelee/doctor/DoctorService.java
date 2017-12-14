package hillelee.doctor;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final JpaDoctorRepository doctorRepository;

    public List<Doctor> getDoctors(Optional<String> spec,
                                   Optional<String> name) {
        if (spec.isPresent() && name.isPresent()) {
            return doctorRepository.findBySpecializationAndName(spec.get(), name.get());
        }
        if (spec.isPresent()) {
            return doctorRepository.findBySpecialization(spec.get());
        }
        if (name.isPresent()) {
            return doctorRepository.findByName(name.get());
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

}
