package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Producer {
    private Long id;
    @JsonProperty("name")
    // @JsonProperty is used when there is a mismatch between the request value and the field name.
    private String name;
    private LocalDateTime createdAt;
    @Getter
    private static List<Producer> producers = new ArrayList<>();

    // static block:
    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madHouse = Producer.builder().id(1L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(mappa, kyotoAnimation, madHouse));
    }
}
