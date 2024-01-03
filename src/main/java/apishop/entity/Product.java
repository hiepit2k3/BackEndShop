package  apishop.entity;

import apishop.util.enums.Gender;
import apishop.util.enums.Season;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("products")
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private Season season;
    private Gender gender;
    private Integer orderCount;
    private Integer viewCount;
    private Binary image;
    private Category category;
    @DBRef
    private ProductVariant productVariants;
    @DBRef
    private HashtagOfProduct hashtagOfProducts;
    @DBRef
    private Set<Evaluate> evaluates;
    @DBRef
    private Brand brand;

}
