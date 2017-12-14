package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

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


    public Pet(String name, String specie, Integer age) {
        this.name = name;
        this.specie = specie;
        this.age = age;
    }

    /*public Optional<String> getName() {
        return Optional.ofNullable(name);
    }*/
}
