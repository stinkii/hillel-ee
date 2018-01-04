package hillelee.pet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaPetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findById(Integer id);

    Page<Pet> findBySpecieAndAgeAndBirthDate(String s, Integer integer, LocalDate date, Pageable pageable);

    Page<Pet> findBySpecie(String s, Pageable sort);

    Page<Pet> findByAge(Integer integer, Pageable sort);

    Page<Pet> findByBirthDate(LocalDate date, Pageable sort);

    @Query("SELECT pet FROM Pet AS pet " +
            "WHERE (pet.specie = :specie OR :specie IS NULL)" +
            " AND (pet.age = :age OR :age IS NULL) ")
    List<Pet> findNullableBySpecieAndAge(@Param("specie") String specie,
                                         @Param("age") Integer age);
}
