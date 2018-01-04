package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Appointment {
    private LocalDate comingDate;
    private LocalTime time;
    private Integer petId;
}
