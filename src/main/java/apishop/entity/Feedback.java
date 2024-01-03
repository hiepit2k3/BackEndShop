package  apishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("feedbacks")
@Builder
public class Feedback {

    @Id
    private String id;
    private String phoneNumber;
    private String email;
    private String description;
    private Date date;
    private Boolean status;
    @DBRef
    private Problem problem;
}