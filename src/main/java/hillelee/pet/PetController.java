package hillelee.pet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by JavaEE on 25.11.2017.
 */

@RestController
public class PetController {

    @GetMapping(value = "/greeting")
    public String greeting(){
        return "Hello!";

    }
}
