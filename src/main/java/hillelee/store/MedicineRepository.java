package hillelee.store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Integer>{

    Optional<Medicine> findByName(String name);
}
