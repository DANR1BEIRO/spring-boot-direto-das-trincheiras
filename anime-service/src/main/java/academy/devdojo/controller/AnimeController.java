package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request_INPUT.AnimePostRequest;
import academy.devdojo.request_INPUT.AnimePutRequest;
import academy.devdojo.response_OUTPUT.AnimeGetResponse;
import academy.devdojo.response_OUTPUT.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {
    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;
    private AnimeService service;

    public AnimeController() {
        service = new AnimeService();
    }

    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request to list all animes, param name '{}'", name);

        List<Anime> animeList = service.findAll(name);

        List<AnimeGetResponse> animeGetResponseList = MAPPER.toAnimeGetResponseList(animeList);

        return ResponseEntity.ok(animeGetResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable long id) {
        log.debug("Request to find Anime by id: '{}'", id);

        Anime anime = service.findByIdOrThrowNotFound(id);

        AnimeGetResponse animeGetResponse = MAPPER.toAnimeGetResponse(anime);

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest animePostRequest) {
        log.debug("Request to save anime: '{}'", animePostRequest);

        Anime anime = MAPPER.toAnime(animePostRequest);
        service.save(anime);
        var response = MAPPER.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.debug("Request to delete by id: '{}'", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to update anime");

        Anime animeToUpdate = MAPPER.toAnime(request);
        service.update(animeToUpdate);

        return ResponseEntity.noContent().build();
    }
}
