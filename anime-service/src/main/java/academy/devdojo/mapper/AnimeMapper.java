package academy.devdojo.mapper;

import academy.devdojo.domain.Anime;
import academy.devdojo.request_INPUT.AnimePostRequest;
import academy.devdojo.response_OUTPUT.AnimeGetResponse;
import academy.devdojo.response_OUTPUT.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnimeMapper {

    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Anime toAnime(AnimePostRequest animePostRequest);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    AnimePostResponse toAnimePostResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);
}
