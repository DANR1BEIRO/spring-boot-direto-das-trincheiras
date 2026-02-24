package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Repository
@ToString
public class HardCodedRepository {

    private static final List<Producer> PRODUCERS = new ArrayList<>();

    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madHouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        PRODUCERS.addAll(List.of(mappa, kyotoAnimation, madHouse));
    }

    public static List<Producer> listAll() {
        return PRODUCERS;
    }

    public static List<Producer> listByName(Producer request) {
        return PRODUCERS.stream().filter(producer -> producer.getName().equalsIgnoreCase(request.getName())).toList();
    }

    public static Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public static void save(Producer producer) {
        PRODUCERS.add(producer);
    }

    public static void deleteById(Long id) {
        PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst().ifPresent(PRODUCERS::remove);
    }

    public void update(Producer producer) {
        PRODUCERS.remove(producer);
        PRODUCERS.add(producer);
    }
}

