package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@AllArgsConstructor
@Data
public class Anime {
    private Long id;
    private String name;

    public static List<Anime> animeList() {
        AtomicLong atomicLong = new AtomicLong();
        return List.of(
                new Anime(atomicLong.incrementAndGet(), "Akira"),
                new Anime(atomicLong.incrementAndGet(), "Death Note"),
                new Anime(atomicLong.incrementAndGet(), "Berserk"));
    }
}
