package apishop.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class FeedbackDto {

    private String id;
    @NonNull
    private String email;
    @NonNull
    private String description;
    private Date date;
    @NonNull
    private String phoneNumber;
    private Boolean status;
    @NonNull
    private String problemId;
    private String problemName;
}
