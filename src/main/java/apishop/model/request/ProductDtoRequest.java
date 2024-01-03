package apishop.model.request;


import apishop.model.dto.HashtagOfProductDto;
import apishop.model.dto.ProductDto;
import apishop.model.dto.ProductVariantDto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductDtoRequest {
    private ProductDto productDto;
    private List<HashtagOfProductDto> hashtagOfProductsDto;
    private List<ProductVariantDto> productVariantsDto;

}
