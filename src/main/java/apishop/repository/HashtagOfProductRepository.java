package apishop.repository;

import apishop.entity.HashtagOfProduct;
import apishop.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagOfProductRepository extends MongoRepository<HashtagOfProduct, String> {
    List<HashtagOfProduct> findAllByProductId(String productId);
    void deleteAllByProduct(Product product);
}
