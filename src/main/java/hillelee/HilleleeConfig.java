package hillelee;

import hillelee.pet.JpaPetRepository;
import hillelee.pet.Pet;
import hillelee.pet.PetRepository;
import hillelee.pet.PetService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class HilleleeConfig {

    @Bean
    PetService petService(JpaPetRepository petRepository) {

        return new PetService(petRepository);
    }

    @Bean
    CommandLineRunner initDb(JpaPetRepository repository){
        return args -> {
            if (!repository.findAll().isEmpty()){
                return;
            }
            repository.save(new Pet("Tom", "Cat", 3));
            repository.save(new Pet("Jerry", "Mouse", 1));
        };
    }

}
