package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    AnimeHardCodedRepository repository;

    @Mock
    AnimeData animeData;
    private List<Anime> animeList;

    @BeforeEach
    void init() {
        var fma = Anime.builder().id(1L).name("Fullmetal Alchemist").build();
        var attackOnTitan = Anime.builder().id(2L).name("Attack on Titan").build();
        var cowboyBebop = Anime.builder().id(3L).name("Cowboy Bebop").build();
        animeList = new ArrayList<>(List.of(fma, attackOnTitan, cowboyBebop));

        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
    }

    @Test
    @DisplayName("findAll returns a list with all animes")
    void findAll_ReturnsAllAnimes_WhenSuccessful() {

        List<Anime> animes = repository.findAll();
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("findById returns an anime when id exists")
    void findById_ReturnsAnAnime_WhenSuccessful() {
        Anime expectedAnime = animeList.getFirst();
        Optional<Anime> animeById = repository.findById(expectedAnime.getId());

        Assertions.assertThat(animeById)
                .isPresent()
                .contains(expectedAnime);
    }

    @Test
    @DisplayName("findById returns empty when id doesn't exist")
    void findById_ReturnsEmpty_WhenIdDoesNotExists() {

        Long nonExistingId = ThreadLocalRandom.current().nextLong(1000000);
        Optional<Anime> animeById = repository.findById(nonExistingId);

        Assertions.assertThat(animeById).isEmpty();
    }

    @Test
    @DisplayName("findByName returns a list of animes with given name")
    void findByName_ReturnsListOfAnimesWithGivenName_WhenSuccessful() {

        Anime anime = animeList.getFirst();
        List<Anime> listByName = repository.findByName(anime.getName());

        Assertions.assertThat(listByName).contains(anime);
    }

    @Test
    @DisplayName("findByName returns an empty list when name is null")
    void findByName_ReturnsAnEmptyList_WhenNameIsNull() {

        List<Anime> animeNull = repository.findByName(null);

        Assertions.assertThat(animeNull).isNotNull();
    }

    @Test
    @DisplayName("save creates a new anime")
    void save_CreatesANewAnime_WhenSuccessful() {
        Anime animeToSave = Anime.builder().id(ThreadLocalRandom.current().nextLong(1000)).name("Samurai Champloo").build();
        Anime anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

        Optional<Anime> optionalAnime = repository.findById(anime.getId());

        Assertions.assertThat(optionalAnime).isPresent().contains(anime);
    }

    @Test
    @DisplayName("delete removes an anime")
    void delete_RemovesAnAnime_WhenSuccessful() {
        Anime animeToDelete = animeList.getFirst();
        repository.delete(animeToDelete);

        List<Anime> animes = repository.findAll();

        Assertions.assertThat(animes).isNotEmpty().doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update updates an anime")
    void update_UpdatesAnAnime_WhenSuccessful() {
        Anime animeToUpdate = animeList.getFirst();
        animeToUpdate.setName("Hellsing");

        repository.update(animeToUpdate);

        Assertions.assertThat(animeList).contains(animeToUpdate);

        Optional<Anime> animeUpdated = repository.findById(animeToUpdate.getId());

        Assertions.assertThat(animeUpdated).isPresent();
        Assertions.assertThat(animeUpdated.get().getId()).isEqualTo(animeToUpdate.getId());
    }
}


