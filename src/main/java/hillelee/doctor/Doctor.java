package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> specializations;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Appointment> appointments;

    public Doctor(String name, Set<String> specializations, Set<Appointment> appointments) {
        this.name = name;
        this.specializations = specializations;
        this.appointments = appointments;
    }
}
