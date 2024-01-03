package  apishop.entity;

import apishop.util.enums.Size;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("productVariants")
@Builder
public class ProductVariant {

    @Id
    private String id;
    private Integer quantity;
    private Double price;
    @DBRef
    private Color color;
    private Size size;
    private Binary image;
    private Product product;
    private Set<OrderDetail> orderDetails;
}
