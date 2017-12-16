package hillelee;


import hillelee.doctor.DoctorService;
import hillelee.doctor.JpaDoctorRepository;
import hillelee.pet.*;

import org.apache.tomcat.jni.Local;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @Bean
    CommandLineRunner initDb(JpaPetRepository repository) {
        return args -> {
            if (!repository.findAll().isEmpty()) {
                return;
            }

            List <Prescription> tomsPrescriptions= Arrays.asList(
                    new Prescription("paracetamol", LocalDate.now(), 3),
                    new Prescription("aspirin", LocalDate.now(), 4)
            );
            List <Prescription> jerrysPrescriptions= Arrays.asList(
                    new Prescription("paracetamol", LocalDate.now(), 3)
            );


            MedicalCard tomsCard = new MedicalCard(LocalDate.now(), "bla-bla");
            MedicalCard jerrysCard = new MedicalCard(LocalDate.now(), "foo-bar");
            repository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), tomsCard,tomsPrescriptions));
            repository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), jerrysCard, jerrysPrescriptions));
        };
    }
}
