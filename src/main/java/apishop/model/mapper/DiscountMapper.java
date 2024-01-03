package apishop.model.mapper;

import apishop.entity.Category;
import apishop.entity.Discount;
import apishop.model.dto.DiscountDto;
import apishop.util.FileConverter;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DiscountMapper implements Function<Discount, DiscountDto> {


    @Override
    public DiscountDto apply(Discount discount) {
        return DiscountDto
                .builder()
                .id(discount.getId())
                .discount(discount.getDiscount())
                .categoryId(discount.getCategory().getId())
                .expirationDate(discount.getExpirationDate())
                .registerDate(discount.getRegisterDate())
                .description(discount.getDescription())
                .image(Base64.getEncoder().encodeToString(discount.getImage().getData()))
                .build()
                ;
    }

    public Discount applyToDiscount(DiscountDto discountDto, Category category) throws IOException {
        if (discountDto.getImageFile() != null) {
            Binary image = FileConverter.convertMultipartFileToBinary(discountDto.getImageFile());
            return Discount
                    .builder()
                    .id(discountDto.getId())
                    .discount(discountDto.getDiscount())
                    .category(category)
                    .expirationDate(discountDto.getExpirationDate())
                    .registerDate(discountDto.getRegisterDate())
                    .description(discountDto.getDescription())
                    .image(image)
                    .build();
        }
        // Chuyển đổi từ Base64 về dữ liệu binary
        byte[] Data = Base64.getDecoder().decode(discountDto.getImage());
        return Discount.builder()
                .id(discountDto.getId())
                .discount(discountDto.getDiscount())
                .category(category)
                .expirationDate(discountDto.getExpirationDate())
                .registerDate(discountDto.getRegisterDate())
                .description(discountDto.getDescription())
                .image(new Binary(Data))
                .build();
    }
}
