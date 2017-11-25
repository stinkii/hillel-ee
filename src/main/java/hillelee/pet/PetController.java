package hillelee.pet;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by JavaEE on 25.11.2017.
 */
public class PetController {

    @GetMapping(value = "/greeting")
    public String greeting(){
        return "Hello!";

    }
}
