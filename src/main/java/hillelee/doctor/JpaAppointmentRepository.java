package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAppointmentRepository extends JpaRepository<Appointment, Integer>{

}
