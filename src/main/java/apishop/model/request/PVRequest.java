package apishop.model.request;

import apishop.model.dto.ProductVariantDto;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"imageFile", "data" })
@NoArgsConstructor
@ToString
public class PVRequest {

    @JsonProperty("imageFile")
    @JsonIgnore
    private MultipartFile imageFile;

    @JsonProperty("data")
    private ProductVariantDto data;

    List<PVRequest> pvRequests;
}
