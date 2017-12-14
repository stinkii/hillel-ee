package hillelee.doctor;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getDoctors(Optional<String> spec,
                                   Optional<String> name) {
        Predicate<Doctor> specFilter = spec.map(this::filterBySpecialization)
                .orElse(doctor -> true);

        Predicate<Doctor> nameFilter = name.map(this::filterByName)
                .orElse(doctor -> true);

        Predicate<Doctor> complexFilter = specFilter.and(nameFilter);

        return doctorRepository.findAll().stream().filter(complexFilter).collect(Collectors.toList());
    }

    private Predicate<Doctor> filterBySpecialization(String spec) {
        return doctor -> doctor.getSpecialization().equals(spec);
    }

    private Predicate<Doctor> filterByName(String name) {
        return doctor -> doctor.getName().startsWith(name);
    }

    public Optional<Doctor> getById(String id) {
        return doctorRepository.findById(id);
    }

    public Doctor save(Doctor doctor) {

        return doctorRepository.save(doctor);

    }

    public Optional<Doctor> delete(String id) {
        return doctorRepository.delete(id);
    }


    public Boolean doctorExists(String id) {
        return doctorRepository.findAll().stream().anyMatch(doc -> doc.getId().equals(id));
    }

}
