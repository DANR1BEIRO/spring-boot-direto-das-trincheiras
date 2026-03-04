package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@Log4j2
@TestMethodOrder(MethodOrderer.class)
class AnimeServiceTest {

    @InjectMocks
    AnimeService service;

    @Mock
    AnimeHardCodedRepository repository;
    List<Anime> animeList;

    @BeforeEach
    void init() {
        var fma = Anime.builder().id(1L).name("Fullmetal Alchemist").build();
        var dragonBall = Anime.builder().id(2L).name("Dragon Ball").build();
        var shurato = Anime.builder().id(3L).name("Shurato").build();
        animeList = new ArrayList<>(List.of(fma, dragonBall, shurato));
    }

    @Test
    @DisplayName("findAll returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAListOfAllAnimes_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(animeList);

        List<Anime> animes = service.findAll(null);

        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("findAll returns a list with founded object when name exists")
    @Order(2)
    void findAll_ReturnsAListWithFoundObject_WhenNameIsFound() {
        Anime anime = animeList.getFirst();
        List<Anime> expectedAnimeFound = Collections.singletonList(anime);

        BDDMockito.when(repository.findByName(anime.getName())).thenReturn(expectedAnimeFound);

        List<Anime> animesFound = service.findAll(anime.getName());

        Assertions.assertThat(animesFound).contains(anime);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() {
        String name = "not-found";

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        List<Anime> emptyList = service.findAll(name);
        log.info(emptyList);

        Assertions.assertThat(emptyList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByIdOrThrowNotFound returns an anime with given id")
    @Order(4)
    void findByIdOrThrowNotFound_ReturnsAnAnime_WhenIdIsValid() {
        Anime expectedAnime = animeList.getFirst();

        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));

        Anime anime = service.findByIdOrThrowNotFound(expectedAnime.getId());

        Assertions.assertThat(anime).isEqualTo(expectedAnime).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("findByIdOrThrowNotFound throws ResponseStatusExceptions when id is not found")
    @Order(5)
    void findByIdOrThrowNotFound_ThrowsResponseStatusException_WhenIdIsNotFound() {
        Anime expectedAnime = animeList.getFirst();
        BDDMockito.when(repository.findById(expectedAnime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates an anime")
    @Order(6)
    void save_CreatesNewAnime_WhenSuccessful() {
        Anime animeToSave = Anime.builder().id(4L).name("One Piece").build();
        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

        Anime savedAnime = service.save(animeToSave);

        Assertions.assertThat(animeToSave).isEqualTo(savedAnime).hasNoNullFieldsOrProperties();

        // verifica se o metodo do repository foi chamado uma vez
        BDDMockito.verify(repository, times(1)).save(animeToSave);
    }

    @Test
    @DisplayName("delete removes an anime")
    @Order(7)
    void delete_RemovesAnime_WhenIdIsFound() {
        Anime animeToDelete = animeList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.of(animeToDelete));

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusExceptions when id is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenIdIsNotFound() {
        Anime animeToDelete = animeList.getFirst();
        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates an anime")
    @Order(9)
    void update_UpdatesAnAnime_WhenSuccessful() {
        Anime animeToUpdate = animeList.getFirst();
        animeToUpdate.setName("Cavaleiros do zodíaco");

        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.of(animeToUpdate));
        BDDMockito.doNothing().when(repository).update(animeToUpdate);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(animeToUpdate));

        BDDMockito.verify(repository, atLeastOnce()).update(animeToUpdate);
    }

    @Test
    @DisplayName("update throws ResponseStatusExceptions when id is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenIdIsNotFound() {
        Anime animeToUpdate = animeList.getFirst();

        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}