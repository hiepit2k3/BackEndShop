package apishop.model.dto;
import apishop.util.enums.TypeDiscount;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class VoucherDto {
    private String id;
    @NonNull
    private String name;
    @NonNull
    private Double discount;
    @NonNull
    private Integer quantity;
    private String description;
    private String image;

    @NonNull
    private Date registerDate;
    @NonNull
    private Date expirationDate;
    @NonNull
    private Double minTotal;
    @NonNull
    private Double maxDiscount;
    @NonNull
    private TypeDiscount typeDiscount;
    private MultipartFile imageFile;


}
