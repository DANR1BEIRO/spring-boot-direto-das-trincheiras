package academy.devdojo.response_OUTPUT;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnimePostResponse {
    private String id;
    private String name;
}
