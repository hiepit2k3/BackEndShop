package  apishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("hastagsOfProduct")
@Builder
public class HashtagOfProduct {

    @Id
    private String id;
    @DBRef
    private Product product;
    @DBRef
    private Hashtag hashtag;
}