package apishop.model.mapper;

import apishop.entity.Hashtag;
import apishop.entity.HashtagOfProduct;
import apishop.entity.Product;
import apishop.model.dto.HashtagOfProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class HashtagOfProductMapper implements Function<HashtagOfProduct, HashtagOfProductDto> {

    @Override
    public HashtagOfProductDto apply(HashtagOfProduct hashtagOfProduct) {
        return HashtagOfProductDto.builder()
                .id(hashtagOfProduct.getId())
                .productId(hashtagOfProduct.getProduct().getId())
                .hashtagId(hashtagOfProduct.getHashtag().getId())
                .build();
    }

    public HashtagOfProduct applyToHashtagOfProduct(Product product, Hashtag hashtag) {
        return HashtagOfProduct
                .builder()
                .id(null)
                .product(product)
                .hashtag(hashtag)
                .build();
    }

    public List<HashtagOfProduct> applyAllToHashtagOfProduct(
            Product product,
            List<Hashtag> hashtag) {
        List <HashtagOfProduct> hashtagOfProducts = new ArrayList<>();
        for (Hashtag h : hashtag) {
            hashtagOfProducts.add(applyToHashtagOfProduct(product, h));
        }
        return hashtagOfProducts;
    }


}
