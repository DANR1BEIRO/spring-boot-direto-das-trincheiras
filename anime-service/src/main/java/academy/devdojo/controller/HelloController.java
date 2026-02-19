package academy.devdojo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/greetings")
@Slf4j
// @RequestMapping( "greetings") A request at class level
public class HelloController {

    @GetMapping()
    public String hi() {
        return "OMAE WA MOU SHINDE IRU";
    }


    // @PostMapping is a Spring annotation that maps HTTP POST requests to a specific handler method in a controller.
    // @RequestBody is used to bind the request body to a method parameter.
    // It tells Spring to deserialize the incoming JSON (or other format) into a Java object.
    // In this case, it binds the request body to the 'name' parameter of type String.
    @PostMapping
    public Long save(@RequestBody String name) {
        log.info("save '{}'", name);
        return ThreadLocalRandom.current().nextLong(1, 1000);
    }
}
