package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request_INPUT.ProducerPostRequest;
import academy.devdojo.request_INPUT.ProducerPutRequest;
import academy.devdojo.response_OUTPUT.ProducerGetResponse;
import academy.devdojo.response_OUTPUT.ProducerPostResponse;
import academy.devdojo.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
@RequiredArgsConstructor
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
    private final ProducerService service;

    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request to list all producers. Param name '{}'", name);
        List<Producer> producers = service.findAll(name);

        List<ProducerGetResponse> producerGetResponses = MAPPER.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable long id) {
        log.debug("Request to find producer by id: '{}'", id);

        Producer producer = service.findByIdOrThrowNotFound(id);
        ProducerGetResponse producerGetResponse = MAPPER.toProducerGetResponse(producer);
        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", headers = "x-api-key")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.debug("Request to save producer: '{}'", producerPostRequest);

        var producer = MAPPER.toProducer(producerPostRequest);
        Producer producerSaved = service.save(producer);
        ProducerPostResponse producerPostResponse = MAPPER.toProducerPostResponse(producerSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        log.debug("Request to delete producer by id: '{}'", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update producer");

        Producer producerToUpdate = MAPPER.toProducer(request);
        service.update(producerToUpdate);
        return ResponseEntity.noContent().build();
    }
}
