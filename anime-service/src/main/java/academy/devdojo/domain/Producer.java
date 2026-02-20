package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producer {
    private Long id;
    @JsonProperty("name") // @JsonProperty is used when there is a mismatch between the request value and the field name.
    private String name;
    @Getter
    private static List<Producer> producers = new ArrayList<>();

    static {
        var mappa = new ProducerBuilder().id(ThreadLocalRandom.current().nextLong(100_000)).name("Mappa").build();
        var kyotoAnimation = new ProducerBuilder().id(ThreadLocalRandom.current().nextLong(100_000)).name("Kyoto Animation").build();
        var madHouse = new ProducerBuilder().id(ThreadLocalRandom.current().nextLong(100_000)).name("Madhouse").build();
        producers.addAll(List.of(mappa, kyotoAnimation, madHouse));
    }
}
