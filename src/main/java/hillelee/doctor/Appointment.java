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
    private Integer petId;
    private LocalTime time;

    public Appointment(LocalDate comingDate, Integer petId, LocalTime time) {
        this.comingDate = comingDate;
        this.petId = petId;
        this.time = time;
    }
}
