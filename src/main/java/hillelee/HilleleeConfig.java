package hillelee;


import hillelee.doctor.*;
import hillelee.pet.*;

import hillelee.store.Medicine;
import hillelee.store.MedicineRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Configuration
public class HilleleeConfig {

    @Bean
    CommandLineRunner initDb(JpaPetRepository repository) {
        return args -> {
            if (!repository.findAll().isEmpty()) {
                return;
            }

            List<Prescription> tomsPrescriptions = Arrays.asList(
                    new Prescription("paracetamol", LocalDate.now(), 3),
                    new Prescription("aspirin", LocalDate.now(), 4)
            );
            List<Prescription> jerrysPrescriptions = Arrays.asList(
                    new Prescription("paracetamol", LocalDate.now(), 3)
            );


            MedicalCard tomsCard = new MedicalCard(LocalDate.now(), "bla-bla");
            MedicalCard jerrysCard = new MedicalCard(LocalDate.now(), "foo-bar");
            repository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), tomsCard, tomsPrescriptions));
            repository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), jerrysCard, jerrysPrescriptions));
        };
    }

    @Bean
    CommandLineRunner initDoctorDb(JpaDoctorRepository repository) {
        return args -> {
            if (!repository.findAll().isEmpty()) {
                return;
            }

            Set<String> specializations = new HashSet<String>() {{
                add("Surgeon");
                add("Dentist");
            }};

            Set<Appointment> appointments = new HashSet<Appointment>() {{
                add(new Appointment(LocalDate.now(), LocalTime.of(9, 0),8));
            }};

            repository.save(new Doctor("Alex", specializations, appointments));
        };
    }

    @Bean
    CommandLineRunner initStoreDb(MedicineRepository repository) {
        return args -> {
            if (!repository.findAll().isEmpty()) {
                return;
            }
            repository.save(new Medicine("Brilliant green",1));
        };
    }
}
