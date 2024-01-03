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
@Document("payments")
@Builder
public class Payment {
    @Id
    private String id;
    private String name;
    private Binary image;
    private String description;
    @DBRef
    private Set<Order> orders;
    public Payment(String id, String name, Binary image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;

    }
}
