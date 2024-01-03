package  apishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("categories")
@Builder
public class Category {

    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private Set<Product> products;
    @DBRef
    private Set<Discount> discount;
}