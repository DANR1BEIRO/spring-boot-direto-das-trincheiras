package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
                .filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Producer findById(@PathVariable long id) {
        return Producer.getProducers().stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     *
     * produces the media type(s) the endpoint produces (application/json)
     * consumes the media type(s) the endpoint consumes (application/json)
     * @param headers the HTTP headers required for the request (x-api-key)
     */
    @PostMapping(produces = "application/json", consumes = "application/json", headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);
        var producer = Producer.builder()
                .id(ThreadLocalRandom.current().nextLong(100_000))
                .name(producerPostRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();

        Producer.getProducers().add(producer);

        ProducerGetResponse response = ProducerGetResponse.builder()
                .id(producer.getId())
                .name(producer.getName())
                .createdAt(producer.getCreatedAt())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
