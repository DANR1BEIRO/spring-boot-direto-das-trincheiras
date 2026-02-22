package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request_INPUT.ProducerPostRequest;
import academy.devdojo.response_OUTPUT.ProducerGetResponse;
import academy.devdojo.response_OUTPUT.ProducerPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request to list all producers. Param name '{}'", name);

        List<Producer> producersFiltered = Producer.getProducers().stream()
                .filter(producer -> name == null || producer.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(MAPPER.toProducerGetResponseList(producersFiltered));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable long id) {
        log.debug("Request to find producer by id: '{}'", id);

        ProducerGetResponse response = Producer.getProducers().stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .map(MAPPER::toProducerGetResponse)
                .orElse(null);

        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", headers = "x-api-key")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest
                                                             producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.debug("Request to save producer: '{}'", producerPostRequest);

        var producer = MAPPER.toProducer(producerPostRequest);
        Producer.getProducers().add(producer);
        var response = MAPPER.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
