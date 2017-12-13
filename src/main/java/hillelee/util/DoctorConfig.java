package hillelee.util;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "doctor-config")
public class DoctorConfig {
    private List<String> specializations = new ArrayList<>();
}
