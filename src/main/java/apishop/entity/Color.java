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
@Document("colors")
@Builder
public class Color {

    @Id
    private String id;
    private String name;
    @DBRef
    private Set<ProductVariant> productVariants;
}
