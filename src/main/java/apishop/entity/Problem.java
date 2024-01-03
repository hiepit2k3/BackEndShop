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
@Document("problems")
@Builder
public class Problem {

    @Id
    private String id;
    private String name;
    @DBRef
    private Set<Feedback> feedbacks;
}
