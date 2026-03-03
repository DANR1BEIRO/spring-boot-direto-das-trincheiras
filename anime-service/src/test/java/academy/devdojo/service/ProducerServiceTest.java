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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}