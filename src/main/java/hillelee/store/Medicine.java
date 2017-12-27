package hillelee.store;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    private Integer version;
    private String name;
    private Integer quantity;

    public Medicine(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
