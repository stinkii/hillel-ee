package hillelee.pet;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {
    @Autowired
    JpaPetRepository petRepository;

    @Autowired
    MockMvc mockMvc;

    @After
    public void cleanup{
        petRepository.deleteAll();
    }

    @Test
    public void getAllPets() throws Exception{
        petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null));

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Tom")));

    }

    @Test
    public void sortByAge() throws Exception{
        petRepository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), null, null));
        petRepository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), null, null));

        mockMvc.perform(get("/pets")
                .param("sort","age"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(2)))
                .andExpect(jsonPath("$.content[0].age", is(1)))
                .andExpect(jsonPath("$.content[1].age", is(3)));
    }
}