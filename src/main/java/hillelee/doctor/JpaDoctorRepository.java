package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer>{
    Optional<Doctor> findById(Integer id);

    /*@Query("SELECT doctor FROM Doctor AS doctor " +
            "WHERE (doctor.specialization IN :specializations)" +
            " AND (doctor.name = :name) ")
    List<Doctor> findBySpecializationAndName(@Param("specializations") List<String> specializations,
                                             @Param("name") String name);
*/
    List<Doctor> findBySpecializationInAndName(List<String> specializations,String name);

    List<Doctor> findBySpecializationIgnoreCaseIn(List<String> specializations);

    List<Doctor> findByNameIgnoreCase(String name);


}
