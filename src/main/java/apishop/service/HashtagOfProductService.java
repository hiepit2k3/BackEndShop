package apishop.service;

import apishop.entity.Hashtag;
import apishop.entity.Product;
import apishop.model.dto.HashtagOfProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HashtagOfProductService {

    List<HashtagOfProductDto> saveAll(
            Product product,
            List<Hashtag> hashtags,
            boolean isUpdate);

    List<HashtagOfProductDto> findByProductId(String id);

}
