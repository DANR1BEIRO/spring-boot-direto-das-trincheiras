package academy.devdojo.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Anime {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
}
