package apishop.model.response;

import lombok.*;
import org.bson.types.Binary;

@Data
@Builder
@NoArgsConstructor
@ToString
public class ProductResponse {

    private String id;
    private String name;
    private Integer order_count;
    private Double min_price;
    private Double max_price;
    private Double rate;
    private Binary main_image;
    private Double discount;
    private Integer quantity;
    private String image;

    public ProductResponse(String id, String name, Integer orderCount, Double minPrice, Double maxPrice, Double rate, Binary mainImage, Double discount, Integer quantity,String image) {
        this.id = id;
        this.name = name;
        this.order_count = orderCount;
        this.min_price = minPrice;
        this.max_price = maxPrice;
        this.rate = rate;
        this.main_image = mainImage;
        this.discount = discount;
        this.quantity = quantity;
        this.image = image;
    }


}
