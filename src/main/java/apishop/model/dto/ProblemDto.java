package apishop.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ProblemDto {

    private String id;
    @NonNull
    private String name;
}
