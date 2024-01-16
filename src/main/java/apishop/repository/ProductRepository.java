package apishop.repository;

import apishop.entity.Product;
import apishop.model.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

//    @Aggregation({
//            "{$lookup: {\n" +
//                    "            from: 'productVariants',\n" +
//                    "            localField: 'id',\n" +
//                    "            foreignField: 'product.id',\n" +
//                    "            as: 'productVariants'\n" +
//                    "        }}",
//            "{$unwind: '$productVariants'}",
//            "{$group: {\n" +
//                    "            _id: '$_id',\n" +
//                    "            name: { $first: '$name' },\n" +
//                    "            order_count: { $first: '$orderCount' },\n" +
//                    "            main_image: { $first: '$image' },\n" +
//                    "        }}",
//            "{$project: {\n" +
//                    "            id: '$_id',\n" +
//                    "            name: '$name',\n" +
//                    "            order_count: 1,\n" +
//                    "            main_image: '$main_image',\n" +
//                    "        }}"
//    })
//    List<ProductResponse> product(Pageable pageable);

    //    @Aggregation({
//            "{$lookup: {" +
//                    "from: 'productVariants'," +
//                    "localField: 'id'," +
//                    "foreignField: 'product.id'," +
//                    "as: 'productVariants'" +
//                    " }}",
//            "{$unwind: '$productVariants'}",
//            "{$group: {" +
//                    "_id: {" +
//                        "productId: '$id'," +
//                        "name: '$name'," +
//                        "order_count: '$orderCount'," +
//                        "main_image: '$image'" +
//                    "}," +
//                    "min_price: { $min: '$productVariants.price' }, " +
//                    "max_price: { $max: '$productVariants.price' }" +
//                    "}}",
//            "{$project: {" +
//                    "_id:0," +
//                    "id:'$_id.productId'," +
//                    "name:'$_id.name'," +
//                    "order_count:'$_id.order_count'," +
//                    "main_image:'$_id.main_image'," +
//                    "min_price:1," +
//                    "max_price:1" +
//                    "}}"
//    })
    @Aggregation({
            "{$lookup: {" +
                    "from: 'productVariants'," +
                    "localField: 'id'," +
                    "foreignField: 'product.id'," +
                    "as: 'productVariants'" +
                    " }}",
            "{$unwind: '$productVariants'}",
            "{$group: {" +
                    "_id: {" +
                        "productId: '$_id'," +
                        "name: '$name'," +
                        "order_count: '$orderCount'," +
                        "main_image: '$image'" +
                    "}," +
                    "min_price: { $min: '$productVariants.price' }, " +
                    "max_price: { $max: '$productVariants.price' }" +
                    "}}",
            "{$project: {" +
                    "_id: 0," +
                    "id: '$_id.productId'," +
                    "name: '$_id.name'," +
                    "order_count: '$_id.order_count'," +
                    "main_image: '$_id.main_image'," +
                    "min_price: '$min_price'," +
                    "max_price: '$max_price'" +
                    "}}"
    })
    Slice<ProductResponse> product(Pageable pageable);


    @Aggregation({
            "{$lookup: {from: 'ProductVariant', localField: 'id', foreignField: 'product.id', as: 'product_variant'}}",
            "{$unwind: '$product_variant'}",
            "{$lookup: {from: 'OrderDetail', localField: 'product_variant.id', foreignField: 'productVariant.id', as: 'order_detail'}}",
            "{$unwind: '$order_detail'}",
            "{$lookup: {from: 'Order', localField: 'order_detail.order.id', foreignField: 'id', as: 'order'}}",
            "{$unwind: '$order'}",
            "{$lookup: {from: 'Account', localField: 'order.account.id', foreignField: 'id', as: 'account'}}",
            "{$unwind: '$account'}",
            "{$match: {'account.id': ?0, 'id': ?1, 'order.typeOrder': 'SUCCESSFUL'}}",
            "{$project: {}}"
    })
    Product findByIdWithAccountHaveOrder(String accountId, String id);

//    Object[] getTopProducts(@Param("TopCount") Integer topCount);
}
