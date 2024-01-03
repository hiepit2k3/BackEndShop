package apishop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BrandDto {

    private String id;
    private String name;
    private String description;
    private String image;
    private MultipartFile imageFile;
    private Boolean isUpdate;
}
