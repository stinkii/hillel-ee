package hillelee;


import hillelee.doctor.*;
import hillelee.pet.*;

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
    PetService petService(JpaPetRepository petRepository) {
        return new PetService(petRepository);
    }

    @Bean
    DoctorService doctorService(JpaDoctorRepository doctorRepository, JpaAppointmentRepository appointmentRepository) {

        return new DoctorService(doctorRepository, appointmentRepository);
    }

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
                add(new Appointment(LocalDate.now(), 2, LocalTime.of(9, 10)));
            }};

            repository.save(new Doctor("Alex", specializations, appointments));
        };
    }
}
