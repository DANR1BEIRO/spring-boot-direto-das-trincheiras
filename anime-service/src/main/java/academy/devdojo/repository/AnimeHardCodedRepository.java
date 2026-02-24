package academy.devdojo.repository;

import academy.devdojo.domain.Anime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimeHardCodedRepository {

    private static final List<Anime> ANIMES = new ArrayList<>();

    static {
        var akira = Anime.builder().id(1L).name("Akira").build();
        var deathNote = Anime.builder().id(2L).name("Death Note").build();
        var berserk = Anime.builder().id(3L).name("Berserk").build();
        ANIMES.addAll(List.of(akira, deathNote, berserk));
    }

    public List<Anime> findAll() {
        return ANIMES;
    }

    public List<Anime> findByName(String name) {
        return ANIMES.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst();
    }

    public Anime save(Anime anime) {
        ANIMES.add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        ANIMES.remove(anime);
    }

    public void update(Anime anime) {
        ANIMES.remove(anime);
        ANIMES.add(anime);
    }
}


