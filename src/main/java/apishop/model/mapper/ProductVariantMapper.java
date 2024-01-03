package apishop.model.mapper;

import apishop.entity.Brand;
import apishop.entity.Color;
import apishop.entity.Product;
import apishop.entity.ProductVariant;
import apishop.model.dto.ProductVariantDto;
import apishop.util.FileConverter;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductVariantMapper implements Function<ProductVariant, ProductVariantDto> {

    @Override
    public ProductVariantDto apply(ProductVariant productVariant) {
        return ProductVariantDto.builder()
                .id(productVariant.getId())
                .quantity(productVariant.getQuantity())
                .price(productVariant.getPrice())
                .colorId(productVariant.getColor().getId())
                .size(productVariant.getSize())
                .productId(productVariant.getProduct().getId())
                .image(Base64.getEncoder().encodeToString(productVariant.getImage().getData()))
                .build();
    }

    public ProductVariant applyToProductVariant(
            ProductVariantDto productVariantDto,
            Product product,
            Color color) throws IOException {
        if (productVariantDto.getMultipartFile() != null) {
            Binary image = FileConverter.convertMultipartFileToBinary(productVariantDto.getMultipartFile());
            return ProductVariant.builder()
                    .id(productVariantDto.getId())
                    .price(productVariantDto.getPrice())
                    .color(color)
                    .product(product)
                    .size(productVariantDto.getSize())
                    .quantity(productVariantDto.getQuantity())
                    .image(image)
                    .build();
        }
        byte[] Data = Base64.getDecoder().decode(productVariantDto.getImage());
        return ProductVariant.builder()
                .id(productVariantDto.getId())
                .price(productVariantDto.getPrice())
                .color(color)
                .product(product)
                .size(productVariantDto.getSize())
                .quantity(productVariantDto.getQuantity())
                .image(new Binary(Data))
                .build();
    }

    public List<ProductVariant> applyAllToProductVariant(
            List<ProductVariantDto> productVariantDto,
            Product product,
            List<Color> color) throws IOException {
        List<ProductVariant> productVariants = new ArrayList<>();
        for (int i = 0; i < productVariantDto.size(); i++) {
            productVariants.add(applyToProductVariant(productVariantDto.get(i), product, color.get(i)));
        }
        return productVariants;
    }

    public List<ProductVariantDto> applyAll(List<ProductVariant> productVariants) {
        List<ProductVariantDto> productVariantDtos = new ArrayList<>();
        for (ProductVariant productVariant : productVariants) {
            productVariantDtos.add(apply(productVariant));
        }
        return productVariantDtos;
    }

}
