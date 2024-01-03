package apishop.repository;

import apishop.entity.Order;
import apishop.model.dto.IncomeDto;
import apishop.util.enums.TypeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Page<Order> findAllByAccountId(String accountId, Pageable pageable);

    @Aggregation({
            "{$match: { 'purchaseDate': { $gte: ?0, $lte: ?1 } }}",
            "{$lookup: { from: 'orderdetails', localField: 'order.id', foreignField: '  id', as: 'orderDetails' }}",
            "{$unwind: '$orderDetails'}",
            "{$group: { _id: null, totalProducts: { $sum: '$orderDetails.quantity' } }}",
            "{$project: { 'totalProducts': 1, 'totalOrder': 1}}"
    })
    List<IncomeDto> getIncome(Date dateStart, Date dateEnd);

    @Query("{ $or: [ { 'account.id': ?0 }, { 'account.id': null } ], 'typeOrder': ?1 }")
    Page<Order> findAllByAccountIdAndTypeOrder(String accountId, TypeOrder typeOrder, Pageable pageable);

    @Query("{ $or: [ { 'account.id': ?0 }, { 'account.id': null } ], 'id': ?1 }")
    Order findAllByAccountIdAndId(String accountId, String id);

    List<Order> findAllByDeliveryAddressId(String deliveryAddressId);

    Page<Order> findAllByTypeOrder(TypeOrder typeOrder,Pageable pageable);
}