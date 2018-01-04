package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String specie;
    private Integer age;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    /*@Fetch(FetchMode.JOIN)*/
    private List<Prescription> prescriptions;

    //@Convert(converter = HibernateDateConverter.class)
    private LocalDate birthDate;
    @OneToOne(cascade = CascadeType.ALL)
    /*@Fetch(FetchMode.JOIN)*/
    private MedicalCard medicalCard;


    public Pet(String name, String specie, Integer age, LocalDate birthDate,
               MedicalCard medicalCard, List<Prescription> prescriptions) {
        this.name = name;
        this.specie = specie;
        this.age = age;
        this.birthDate = birthDate;
        this.medicalCard = medicalCard;
        this.prescriptions = prescriptions;
    }
}
