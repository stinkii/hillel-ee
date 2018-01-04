package hillelee.pet.dto;

import hillelee.pet.LatinName;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PrescriptionInputDto {
    private String description;
    private LocalDate start;
    @Range(min = 1, max = 12)
    @NotNull
    private Integer timesPerDay;
    @NotEmpty
    @LatinName
    private String medicineName;
    @NotNull
    @Min(1)
    private Integer quantity;
}
