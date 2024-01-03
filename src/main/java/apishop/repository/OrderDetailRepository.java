package apishop.repository;

import apishop.entity.OrderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetail, String> {
    List<OrderDetail> findAllByOrderId(String orderId);
    @Query("{'id': ?0, 'order.account.id': ?1}")
    OrderDetail findByIdAndAccountId(String id, String accountId);
}
