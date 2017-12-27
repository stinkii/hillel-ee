package hillelee.pet.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrescriptionInputDto {
    private String description;
    private LocalDate start;
    private Integer timesPerDay;
    private String medicineName;
    private Integer quantity;
}
