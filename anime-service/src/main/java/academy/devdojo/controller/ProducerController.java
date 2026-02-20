package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

    @GetMapping()
    public List<Producer> listAll(@RequestParam(required = false) String name) {
        var producers = Producer.getProducers();
        if (name == null) return producers;

        return producers.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Producer findById(@PathVariable long id) {
        return Producer.getProducers().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     *
     * produces the media type(s) the endpoint produces (application/json)
     * consumes the media type(s) the endpoint consumes (application/json)
     * @param headers the HTTP headers required for the request (x-api-key)
     */
    @PostMapping(produces = "application/json", consumes = "application/json", headers = "x-api-key")
    public Producer save(@RequestBody Producer anime, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);
        anime.setId(ThreadLocalRandom.current().nextLong(100_000));
        Producer.getProducers().add(anime);
        return anime;
    }
}
