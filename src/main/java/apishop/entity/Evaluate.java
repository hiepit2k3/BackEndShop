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
@Document( "evaluates")
@Builder
public class Evaluate {

    @Id
    private String id;
    private Integer rate;
    private String comment;
    private Date date;
    private Boolean isUpdate;

   @DBRef
    private Product product;

    @DBRef
    private Account account;

}
