package apishop.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class PaymentDto {

    private String id;
    @NonNull
    private String name;
    private String image;
    private MultipartFile imageFile;
    private String description;
    private Boolean isUpdate;
}
