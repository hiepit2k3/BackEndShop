package apishop.model.dto;

import lombok.*;


@Builder
@AllArgsConstructor
@ToString
@Data
public class HashtagOfProductDto {

    private String id;
    @NonNull
    private String hashtagId;
    private String productId;
}
