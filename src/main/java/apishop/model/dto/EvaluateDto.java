package apishop.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EvaluateDto {

    private String id;
    @NonNull
    private Integer rate;
    private String comment;
    private Date date;
    private Boolean isUpdate;
    @NonNull
    private String productId;
    private String accountId;
}
