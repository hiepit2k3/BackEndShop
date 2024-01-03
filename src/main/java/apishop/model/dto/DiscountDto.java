package apishop.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class DiscountDto {

    private String id;
    private Double discount;
    private Date registerDate;
    private Date expirationDate;
    private String description;
    private String image;
    private MultipartFile imageFile;
    private String categoryId;
    private Boolean isUpdate;
}
