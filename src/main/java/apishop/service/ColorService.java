package apishop.service;

import apishop.model.dto.ColorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColorService {
    List<ColorDto> findAllColor();

    ColorDto findColorById(String colorId);

    ColorDto saveColor(ColorDto colorDto);

    void deleteColorById(String colorId);
}
