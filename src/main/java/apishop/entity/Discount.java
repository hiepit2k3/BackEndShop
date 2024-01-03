package  apishop.entity;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("discounts")
@Builder
public class Discount {
    @Id
    private String id;
    private Double discount;
    private Date registerDate;
    private Date expirationDate;
    private String description;
    private Binary image;
    @DBRef
    private Category category;
}
