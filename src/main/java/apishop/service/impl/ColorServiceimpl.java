package apishop.service.impl;

import apishop.entity.Color;
import apishop.model.dto.ColorDto;
import apishop.model.mapper.ColorMapper;
import apishop.repository.ColorRepository;
import apishop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColorServiceimpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public List<ColorDto> findAllColor() {
        List<Color> colors = colorRepository.findAll();
        return colors.stream().map(colorMapper :: apply).collect(Collectors.toList());
    }

    @Override
    public ColorDto findColorById(String colorId) {
        Optional<Color> color = colorRepository.findById(colorId);
        return color.map(colorMapper::apply).orElse(null);
    }


    @Override
    public ColorDto saveColor(ColorDto colorDto) {
        Color color = colorMapper.applyToColor(colorDto);
        Color newColor = colorRepository.save(color);
        return colorMapper.apply(newColor);
    }

    @Override
    public void deleteColorById(String colorId) {
        colorRepository.deleteById(colorId);
    }

}
