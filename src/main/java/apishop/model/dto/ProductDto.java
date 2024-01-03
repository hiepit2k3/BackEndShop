package apishop.model.dto;

import apishop.util.enums.Gender;
import apishop.util.enums.Season;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ProductDto {
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    private Integer orderCount;
    private Integer viewCount;
    @NonNull
    private Season season;
    @NonNull
    private Gender gender;
    private String image;
    private MultipartFile multipartFile;
    @NonNull
    private String categoryId;
    @NonNull
    private String brandId;

}
