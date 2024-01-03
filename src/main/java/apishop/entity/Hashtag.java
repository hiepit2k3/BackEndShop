package  apishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document( "hashtags")
@Builder
public class Hashtag {

    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private Set<HashtagOfProduct> hashtagOfProducts;
}