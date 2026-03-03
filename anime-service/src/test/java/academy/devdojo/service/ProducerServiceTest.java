package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {

    @InjectMocks
    ProducerService service;

    @Mock
    ProducerHardCodedRepository repository;
    List<Producer> producerList;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var toeyAnimation = Producer.builder().id(2L).name("ToeyAnimation").createdAt(LocalDateTime.now()).build();
        var ghibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producerList = new ArrayList<>(List.of(ufotable, toeyAnimation, ghibli));
    }

    @Test
    @DisplayName("findAll returns all producers when argument is null")
    void findAll_returnAll_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        List<Producer> producers = service.findAll(null);
        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("findAll returns a list of producers when name is found")
    void findAll_returnListOfProducers_WhenNameExists() {

        Producer producer = producerList.getFirst();
        List<Producer> expectedProducersFound = Collections.singletonList(producer);

        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(expectedProducersFound);

        List<Producer> producers = service.findAll(producer.getName());

        Assertions.assertThat(producers).containsAll(expectedProducersFound);
    }

    @Test
    @DisplayName("findAll returns an empty list when name is not found")
    void findAll_returnEmptyList_WhenNameIsNotFound() {
        String name = "not-found";

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        List<Producer> producers = service.findAll(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns Producer with given id")
    void findById_ReturnProducer_WhenSuccessful() {
        Producer expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        Producer producer = service.findByIdOrThrowNotFound(expectedProducer.getId());

        Assertions.assertThat(producer).isEqualTo(expectedProducer);
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when producer is not found")
    void findById_ThrowsResponseStatusException_WhenIdIsNotFound() {
        Producer expectedProducer = producerList.getFirst();
        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save create a new Producer")
    void save_CreateProducer_WhenSuccessful() {
        Producer producerToSave = Producer.builder().id(100L).name("TV manchete").createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);

        Producer savedProducer = service.save(producerToSave);

        Assertions.assertThat(savedProducer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a producer")
    void delete_RemoveProducer_WhenSuccessful() {
        Producer producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.delete(producerToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    void delete_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        Producer producerToDelete = producerList.getFirst();
        BDDMockito.when(repository.findById(producerToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(producerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update updates a producer")
    void update_UpdateProducer_WhenSuccessful() {
        Producer producerToUpdate = producerList.getFirst();
        producerToUpdate.setName("AnimePlex");
        BDDMockito.when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
        BDDMockito.doNothing().when(repository).update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    void update_ResponseStatusException_ResponseStatusException() {
        Producer producerToUpdate = producerList.getFirst();

        BDDMockito.when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(producerToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}









