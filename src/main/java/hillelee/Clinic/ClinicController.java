package hillelee.Clinic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClinicController {

    private String clinicName;
    private String workingHours;

    public ClinicController(@Value("${clinic.name}") String clinicName,
                            @Value("${clinic.working-hours}") String workingHours) {
        this.clinicName = clinicName;
        this.workingHours= workingHours;
    }

    @GetMapping("/clinic/info")
    public ClinicInfo getClinicInfo() {
        return new ClinicInfo(clinicName, workingHours);
    }
}

@Data
@AllArgsConstructor
class ClinicInfo {
    private String name;
    private String workingHours;
}
