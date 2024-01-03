package apishop.model.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class ProductIdentity {
    @NonNull
    private ProductDto productDto;
    @NonNull
    private CategoryDto categoryDto;
    @NonNull
    private BrandDto brandDto;  
    private List<HashtagDto> hashtagDtos;
    @NonNull
    private List<ProductVariantDto> productVariantsDto;
    private Double discount;
}
