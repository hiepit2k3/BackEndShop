package apishop.facade;

import apishop.entity.Hashtag;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.HashtagDto;
import apishop.model.dto.SearchCriteria;
import apishop.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagFacade {

    private final HashtagService hashtagService;

    //Tìm kiếm tất cả hashtag
    public Page<HashtagDto> findAllHashtag(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<HashtagDto> hashtags = hashtagService.findAllHashtag(searchCriteria);
        if (hashtags.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return hashtags;
    }
    //Tìm kiếm hashtag theo id
    public HashtagDto findHashtagById(String hashtagId) throws ArchitectureException {
        HashtagDto hashtag = hashtagService.findHashtagById(hashtagId);
        if (hashtag == null) {
            throw new EntityNotFoundException();
        }
        return hashtag;
    }
    //Tìm kiếm hashtag theo tên
    public Page<HashtagDto> findAllHashtagByName(String hashtagName, SearchCriteria searchCriteria) throws ArchitectureException {
        if (hashtagName.isEmpty()) {
            throw new InvalidParamException();
        }
        Page<HashtagDto> hashtags = hashtagService.findAllHashtagByName(hashtagName, searchCriteria);
        if (hashtags.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return hashtags;
    }
    //Thêm mới hashtag
    public HashtagDto createHashtag(HashtagDto hashtagDto) throws ArchitectureException {
        if (hashtagDto.getId() != null) {
            throw new IdMustBeNullException(Hashtag.class.getSimpleName());
        }
        hashtagDto.setName("#" + hashtagDto.getName());
        return hashtagService.saveHashtag(hashtagDto);
    }
    // Cập nhật hashtag
    public HashtagDto updateHashtag(HashtagDto hashtagDto,String id) throws ArchitectureException {
        findHashtagById(id);
        hashtagDto.setId(id);
        return hashtagService.saveHashtag(hashtagDto);
    }
    //Xóa hashtag
    public void deleteHashtagById(String hashtagId) throws ArchitectureException {
        findHashtagById(hashtagId);
        try {
            hashtagService.deleteHashtagById(hashtagId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CanNotDeleteException("hashtag");
        }
    }
}
