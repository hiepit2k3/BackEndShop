package apishop.repository;

import apishop.entity.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends MongoRepository<Discount, String> {
    @Query("{'category.id': ?0, 'expirationDate': {'$gte': ?1}}")
    Optional<Discount> findByCategoryIdIsActived(String categoryId, Date currentDate);

    List<Discount> findByCategoryId(String categoryId);

    void deleteDiscountById(String aLong);

    @Query("{'expirationDate': {'$gte': ?0}, 'registerDate': {'$lte': ?0}}")
    List<Discount> findAllIsActived(Date currentDate);
}
