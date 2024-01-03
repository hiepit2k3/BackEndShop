package apishop.model.mapper;

import apishop.entity.Brand;
import apishop.entity.Category;
import apishop.entity.Product;
import apishop.model.dto.ProductDto;
import apishop.util.FileConverter;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductMapper implements Function<Product, ProductDto> {
    @Override
    public ProductDto apply(Product product) {
        return ProductDto.
                builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .season(product.getSeason())
                .gender(product.getGender())
                .viewCount(product.getViewCount())
                .orderCount(product.getOrderCount())
                .brandId(product.getBrand().getId())
                .image(Base64.getEncoder().encodeToString(product.getImage().getData()))
                .build();
    }


    public Product applyToProduct(ProductDto productDto, Category category, Brand brand) throws IOException {
        if (productDto.getMultipartFile() != null) {
            Binary image = FileConverter.convertMultipartFileToBinary(productDto.getMultipartFile());
            return Product.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .description(productDto.getDescription())
                    .season(productDto.getSeason())
                    .gender(productDto.getGender())
                    .viewCount(productDto.getViewCount())
                    .orderCount(productDto.getOrderCount() == null ? 0 : productDto.getOrderCount())
                    .image(image)
                    .category(category)
                    .brand(brand)
                    .build();
        }
        byte[] Data = Base64.getDecoder().decode(productDto.getImage());
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .season(productDto.getSeason())
                .gender(productDto.getGender())
                .viewCount(productDto.getViewCount())
                .orderCount(productDto.getOrderCount() == null ? 0 : productDto.getOrderCount())
                .image(new Binary(Data))
                .category(category)
                .brand(brand)
                .build();
    }
}

