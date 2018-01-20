package hillelee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableRetry
@EnableWebSecurity
public class HillelEeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HillelEeApplication.class, args);
    }
}
