package apishop.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class HashtagDto {

    private String id;
    @NonNull
    private String name;
    private String description;

}
