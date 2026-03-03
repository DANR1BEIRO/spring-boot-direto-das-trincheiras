package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;
    private List<Producer> producerList;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var toeyAnimation = Producer.builder().id(2L).name("ToeyAnimation").createdAt(LocalDateTime.now()).build();
        var ghibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producerList = new ArrayList<>(List.of(ufotable, toeyAnimation, ghibli));
    }

    @Test
    @DisplayName("findAll returns a list with all producerList")
    @Order(1)
    void findAll_ReturnAllProducers_WhenSuccesful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        List<Producer> producers = repository.findAll();
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("findById returns a producer with given id")
    @Order(2)
    void findById_ReturnsProducerById_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        Producer expectedProducer = producerList.getFirst();
        Optional<Producer> producer = repository.findById(expectedProducer.getId());
        Assertions.assertThat(producer).isPresent().contains(expectedProducer);
    }

    @Test
    @DisplayName("findByName returns an empty list when name is null")
    @Order(3)
    void findByName_ReturnsAnEmptyList_WhenNameIsNull() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        List<Producer> producersNull = repository.findByName(null);
        Assertions.assertThat(producersNull).isNotNull();
    }

    @Test
    @DisplayName("findByName returns list of producers when name is found")
    @Order(4)
    void findByName_ReturnsListOfProducersWithGivenName_WhenNameIsFound() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        Producer expectedProducer = producerList.getFirst();
        List<Producer> producerListFilteredByName = repository.findByName(expectedProducer.getName());
        Assertions.assertThat(producerListFilteredByName).contains(expectedProducer);
    }

    @Test
    @DisplayName("save creates a producer")
    @Order(5)
    void save_CreatesAProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        Producer producerToSave = Producer.builder().id(100L).name("TV manchete").createdAt(LocalDateTime.now()).build();
        Producer producer = repository.save(producerToSave);

        Assertions.assertThat(producer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();

        Optional<Producer> producerSavedOptional = repository.findById(producer.getId());

        Assertions.assertThat(producerSavedOptional).isPresent().contains(producerToSave);
    }

    @Test
    @DisplayName("delete removes a producer")
    @Order(6)
    void delete_RemoveProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        Producer producerToDelete = producerList.getFirst();
        repository.delete(producerToDelete);

        List<Producer> allProducers = repository.findAll();

        Assertions.assertThat(allProducers).isNotEmpty().doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update updates a producer")
    @Order(7)
    void update_UpdateProducer_WhenSuccessful() {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        Producer producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("Aniplax");

        repository.update(producerToUpdate);

        Assertions.assertThat(producerList).contains(producerToUpdate);

        Optional<Producer> producerUpdated = repository.findById(producerToUpdate.getId());

        Assertions.assertThat(producerUpdated).isPresent();
        Assertions.assertThat(producerUpdated.get().getName()).isEqualTo(producerToUpdate.getName());
    }
}














