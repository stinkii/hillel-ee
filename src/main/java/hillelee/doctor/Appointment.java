package hillelee.doctor;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@Embeddable
public class Appointment {
    private LocalDate comingDate;
    private LocalTime time;
    private Integer petId;

    public Appointment(LocalDate comingDate, LocalTime time, Integer petId) {
        this.comingDate = comingDate;
        this.time = time;
        this.petId = petId;
    }
}
