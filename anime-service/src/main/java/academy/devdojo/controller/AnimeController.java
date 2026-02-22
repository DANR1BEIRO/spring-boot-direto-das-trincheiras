package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request_INPUT.AnimePostRequest;
import academy.devdojo.response_OUTPUT.AnimeGetResponse;
import academy.devdojo.response_OUTPUT.AnimePostResponse;
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

    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request to list all animes, param name '{}'", name);

        List<Anime> animesFiltered = Anime.getAnimes().stream()
                .filter(anime -> name == null || anime.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(MAPPER.toAnimeGetResponseList(animesFiltered));
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable long id) {
        log.debug("Request to find Anime by id: '{}'", id);

        AnimeGetResponse animeGetResponse = Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .map(MAPPER::toAnimeGetResponse)
                .orElse(null);
        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest animePostRequest) {
        log.debug("Request to save anime: '{}'", animePostRequest);

        var anime = MAPPER.toAnime(animePostRequest);
        Anime.getAnimes().add(anime);
        var response = MAPPER.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
