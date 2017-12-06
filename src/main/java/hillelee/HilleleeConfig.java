package hillelee;

import hillelee.doctor.DoctorRepository;
import hillelee.doctor.DoctorService;
import hillelee.pet.PetRepository;
import hillelee.pet.PetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HilleleeConfig {

    @Bean
    PetService petService(PetRepository petRepository) {
        return new PetService(petRepository);
    }

    @Bean
    DoctorService doctorService(DoctorRepository doctorRepository) {
        return new DoctorService(doctorRepository);
    }
}
