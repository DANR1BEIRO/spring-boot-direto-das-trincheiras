package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {
    private static final List<Anime> ANIMES = new ArrayList<>();

    static {
        var akira = Anime.builder().id(1L).name("Akira").build();
        var deathNote = Anime.builder().id(2L).name("Death Note").build();
        var berserk = Anime.builder().id(3L).name("Berserk").build();
        ANIMES.addAll(List.of(akira, deathNote, berserk));
    }

    public List<Anime> getAnimes() {
        return ANIMES;
    }
}
