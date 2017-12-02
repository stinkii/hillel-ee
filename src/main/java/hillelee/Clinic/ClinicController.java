package hillelee.Clinic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ClinicController {

    private final ClinicInfo clinicInfo;

    @GetMapping("/clinic/info")
    public ClinicInfo getClinicInfo() {
        return clinicInfo;
    }
}

@Data
@Component
@ConfigurationProperties(prefix = "clinic-info")
class ClinicInfo {
    private String name;
    private String workingHours;
}
