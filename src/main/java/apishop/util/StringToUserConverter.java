package apishop.util;

import apishop.model.dto.ProductVariantDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToUserConverter implements Converter<String, ProductVariantDto> {

    private final ObjectMapper objectMapper;

    @Override
        @SneakyThrows
        public ProductVariantDto convert(String source) {
            return objectMapper.readValue(source, ProductVariantDto.class);
        }
    }
