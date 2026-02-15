package outside.devdojo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutsiderController {

    @GetMapping("test")
    public String outsider() {
        return "outside controller";
    }
}
