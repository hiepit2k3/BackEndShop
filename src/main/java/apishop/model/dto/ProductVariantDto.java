package apishop.model.dto;

import apishop.util.enums.Size;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ProductVariantDto {
    private String id;
    @NonNull
    private Integer quantity;
    @NonNull
    private Double price;
    @NonNull
    private Size size;
    private String image;
    private MultipartFile multipartFile;
    @NonNull
    private String colorId;
    private String productId;
}
