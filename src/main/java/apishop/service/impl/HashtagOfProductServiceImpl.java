package apishop.service.impl;

import apishop.entity.Hashtag;
import apishop.entity.HashtagOfProduct;
import apishop.entity.Product;
import apishop.model.dto.HashtagOfProductDto;
import apishop.model.mapper.HashtagOfProductMapper;
import apishop.repository.HashtagOfProductRepository;
import apishop.service.HashtagOfProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagOfProductServiceImpl implements HashtagOfProductService {

    private final HashtagOfProductRepository hashtagOfProductRepository;
    private final HashtagOfProductMapper hashtagOfProductMapper;
    @Override
    public List<HashtagOfProductDto> saveAll(Product product, List<Hashtag> hashtags, boolean isUpdate) {
        if (isUpdate)
            hashtagOfProductRepository.deleteAllByProduct(product);

        List<HashtagOfProduct> hashtagOfProducts =
                hashtagOfProductMapper.applyAllToHashtagOfProduct(product, hashtags);

        return hashtagOfProductRepository
                .saveAll(hashtagOfProducts)
                .stream()
                .map(hashtagOfProductMapper::apply)
                .toList();
    }

    @Override
    public List<HashtagOfProductDto> findByProductId(String id){
        return hashtagOfProductRepository
                .findAllByProductId(id)
                .stream()
                .map(hashtagOfProductMapper::apply)
                .toList();
    }

}
