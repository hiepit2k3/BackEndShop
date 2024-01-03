package apishop.model.mapper;

import apishop.entity.Hashtag;
import apishop.model.dto.HashtagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class HashtagMapper implements Function<Hashtag, HashtagDto> {

    @Override
    public HashtagDto apply(Hashtag hashtag) {
        return HashtagDto
                .builder()
                .id(hashtag.getId())
                .name(hashtag.getName())
                .description(hashtag.getDescription())
                .build();
    }

    public Hashtag applyToHashtag(HashtagDto hashtagDto) {
        return Hashtag
                .builder()
                .id(hashtagDto.getId())
                .name(hashtagDto.getName())
                .description(hashtagDto.getDescription())
                .build();
    }

    public List<HashtagDto> applyAll(List<Hashtag> hashtags) {
        System.out.println(hashtags);
        List<HashtagDto> hashtagDtos = new ArrayList<>();
        for (Hashtag hashtag : hashtags) {
            hashtagDtos.add(apply(hashtag));
        }
        return hashtagDtos;
    }
}
