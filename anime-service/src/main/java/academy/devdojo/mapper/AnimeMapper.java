package academy.devdojo.mapper;

import academy.devdojo.domain.Anime;
import academy.devdojo.request_INPUT.AnimePostRequest;
import academy.devdojo.request_INPUT.AnimePutRequest;
import academy.devdojo.response_OUTPUT.AnimeGetResponse;
import academy.devdojo.response_OUTPUT.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Anime toAnime(AnimePostRequest animePostRequest);

    Anime toAnime(AnimePutRequest animePutRequest);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    AnimePostResponse toAnimePostResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);
}
