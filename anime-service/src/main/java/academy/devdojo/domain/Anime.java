package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var akira = new Anime(ThreadLocalRandom.current().nextLong(100_000), "Akira");
        var deathNote = new Anime(ThreadLocalRandom.current().nextLong(100_000), "Death Note");
        var berserk = new Anime(ThreadLocalRandom.current().nextLong(100_000), "Berserk");
        animes.addAll(List.of(akira, deathNote, berserk));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
