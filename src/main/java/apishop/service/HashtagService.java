package apishop.service;

import apishop.model.dto.HashtagDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HashtagService {

    Page<HashtagDto> findAllHashtag(SearchCriteria searchCriteria);

    HashtagDto findHashtagById(String hashtagId);

    Page<HashtagDto> findAllHashtagByName(String hashtagName, SearchCriteria searchCriteria);

    HashtagDto saveHashtag(HashtagDto hashtagDto);

    void deleteHashtagById(String hashtagId);
}
