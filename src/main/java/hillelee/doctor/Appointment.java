package hillelee.doctor;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate comingDate;
    private LocalTime time;
    private Integer petId;

    public Appointment(LocalDate comingDate, LocalTime time, Integer petId) {
        this.comingDate = comingDate;
        this.time = time;
        this.petId = petId;
    }
}
