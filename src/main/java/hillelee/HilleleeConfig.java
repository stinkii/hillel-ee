package hillelee;


import hillelee.doctor.DoctorService;
import hillelee.doctor.JpaDoctorRepository;
import hillelee.pet.JpaPetRepository;

import hillelee.pet.PetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HilleleeConfig {

    @Bean
    PetService petService(JpaPetRepository petRepository) {

        return new PetService(petRepository);
    }

    @Bean
    DoctorService doctorService(JpaDoctorRepository doctorRepository) {

        return new DoctorService(doctorRepository);
    }
}
