package  apishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("orderdetails")
@Builder
public class OrderDetail {

    @Id
    private String id;
    private String name;
    private Integer quantity;
    private Double price;
    private String image;
    private String size;
    private String color;
    private Double discount;
    private Boolean isEvaluate;
    @DBRef
    private Order order;
    @DBRef
    private ProductVariant  productVariant;
}
