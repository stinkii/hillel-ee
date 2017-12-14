package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer>{
    Optional<Doctor> findById(Integer id);

    List<Doctor> findBySpecializationAndName(String spec, String name);

    List<Doctor> findBySpecialization(String spec);

    List<Doctor> findByName(String name);


}
