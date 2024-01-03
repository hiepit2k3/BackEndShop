package apishop.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class CategoryDto {

    private String id;
    private String name;
    private String description;
}
