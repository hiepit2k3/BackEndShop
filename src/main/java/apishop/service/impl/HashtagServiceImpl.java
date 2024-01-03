package apishop.service.impl;

import apishop.entity.Hashtag;
import apishop.model.dto.HashtagDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.HashtagMapper;
import apishop.repository.HashtagRepository;
import apishop.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public Page<HashtagDto> findAllHashtag(SearchCriteria searchCriteria){
        Page<Hashtag> hashtags = hashtagRepository.findAll(getPageable(searchCriteria));
        return hashtags.map(hashtagMapper::apply);
    }

    @Override
    public HashtagDto findHashtagById(String hashtagId) {
        Optional<Hashtag> hashtag = hashtagRepository.findById(hashtagId);
        return hashtag.map(hashtagMapper::apply).orElse(null);
    }

    @Override
    public Page<HashtagDto> findAllHashtagByName(String hashtagName, SearchCriteria searchCriteria) {
        Page<Hashtag> hashtags = hashtagRepository.findByNameContains(hashtagName, getPageable(searchCriteria));
        return hashtags.map(hashtagMapper::apply);
    }

    @Override
    public HashtagDto saveHashtag(HashtagDto hashtagDto) {
        return hashtagMapper.apply(hashtagRepository.save(hashtagMapper.applyToHashtag(hashtagDto)));
    }

    @Override
    public void deleteHashtagById(String hashtagId) {
        hashtagRepository.deleteById(hashtagId);
    }

}
