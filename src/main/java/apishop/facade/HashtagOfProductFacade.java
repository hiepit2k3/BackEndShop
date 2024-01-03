package apishop.facade;

import apishop.entity.Hashtag;
import apishop.entity.Product;
import apishop.exception.common.ForeignKeyIsNotFound;
import apishop.exception.core.ArchitectureException;
import apishop.model.dto.HashtagOfProductDto;
import apishop.repository.HashtagRepository;
import apishop.service.HashtagOfProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagOfProductFacade {

    private final HashtagOfProductService hashtagOfProductService;
    private final HashtagRepository hashtagRepository;

    public List<HashtagOfProductDto> createAll(List<HashtagOfProductDto> hashtagOfProductsDto,
                                               Product product,
                                               boolean isUpdate) throws ArchitectureException {
        List<Hashtag> hashtags = new ArrayList<>();
        // Kiểm tra xem list có chứa id hay không
        for (HashtagOfProductDto hashtagOfProductDto : hashtagOfProductsDto) {

            hashtagOfProductDto.setId(null);
            // Lấy ra các hashtag theo id để kiểm tra có tồn tại không và thêm vào list để lưu hashtagOfProduct
            Optional<Hashtag> hashtag = hashtagRepository.findById(hashtagOfProductDto.getHashtagId());
            if (hashtag.isEmpty())
                throw new ForeignKeyIsNotFound(Hashtag.class.getSimpleName());

            hashtags.add(hashtag.get());
        }
        return hashtagOfProductService.saveAll(product, hashtags, isUpdate);
    }
}
