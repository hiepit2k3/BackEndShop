package apishop.repository;

import apishop.entity.ProductVariant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends MongoRepository<ProductVariant, String> {
    List<ProductVariant> findAllByProductId(String id);

    @Query(value = "{'_id': ?0}", fields = "{'id': 1}")
    Optional<String> getId(String id);

}
