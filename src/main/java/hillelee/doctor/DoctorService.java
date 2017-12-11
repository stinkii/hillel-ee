package hillelee.doctor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import hillelee.util.YamlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        Map<String, Object> specs = getSpecs();

        return doctorRepository.save(doctor);

    }

    public Optional<Doctor> delete(String id) {
        return doctorRepository.delete(id);
    }

    public Map<String, Object> getSpecs() {
        YamlReader reader = new YamlReader();
        Map<String, Object> specs = null;
        try {
            specs = reader.read("src/main/resources/specializations.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return specs;
    }

    public Collection<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

}
