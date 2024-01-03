package apishop.model.mapper;

import apishop.entity.Color;
import apishop.model.dto.ColorDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ColorMapper implements Function<Color, ColorDto> {


    @Override
    public ColorDto apply(Color color) {
        return ColorDto
                .builder()
                .id(color.getId())
                .name(color.getName())
                .build();
    }

    public Color applyToColor(ColorDto colorDto) {
        return Color
                .builder()
                .id(colorDto.getId())
                .name(colorDto.getName())
                .build();
    }
}
