package hillelee.doctor;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DoctorController {

    private List<Doctor> doctors = new ArrayList<Doctor>() {{

    }};


    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return doctors;
    }
}
