package  apishop.entity;


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
@Document(collection = "brands")
@Builder
public class Brand {

    @Id
    private String id;
    private String name;
    private String description;
    private Binary image;
    @DBRef
    private Set<Product> products;
}
