package apishop.model.mapper;

import apishop.entity.Brand;
import apishop.model.dto.BrandDto;
import apishop.util.FileConverter;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.function.Function;

@Service
public class BrandMapper implements Function<Brand, BrandDto> {

    @Override
    public BrandDto apply(Brand brand) {
        return BrandDto
                .builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .image(Base64.getEncoder().encodeToString(brand.getImage().getData()))
                .build();
    }

    public Brand applyToBrand(BrandDto brandDto) throws IOException {
        if (brandDto.getImageFile() != null) {
            Binary image = FileConverter.convertMultipartFileToBinary(brandDto.getImageFile());
            return Brand.builder()
                    .id(brandDto.getId())
                    .description(brandDto.getDescription())
                    .name(brandDto.getName())
                    .image(image)
                    .build();
        }
        // Chuyển đổi từ Base64 về dữ liệu binary
        byte[] Data = Base64.getDecoder().decode(brandDto.getImage());
        return Brand.builder()
                .id(brandDto.getId())
                .description(brandDto.getDescription())
                .name(brandDto.getName())
                .image(new Binary(Data))
                .build();
    }
}
