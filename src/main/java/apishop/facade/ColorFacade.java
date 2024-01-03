package apishop.facade;

import apishop.exception.common.CanNotDeleteException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.ColorDto;
import apishop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorFacade {

    private final ColorService colorService;

    public List<ColorDto> findAll() throws ArchitectureException {
        List<ColorDto> listColor = colorService.findAllColor();
        if (listColor.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return listColor;
    }

    public ColorDto findColorById(String colorId) throws ArchitectureException {
        return checkNotNull(colorId);
    }

    public ColorDto createColor(ColorDto colorDto) {
        return colorService.saveColor(colorDto);
    }

    public ColorDto updateColor(String colorId, ColorDto colorDto) throws ArchitectureException {
        checkNotNull(colorId);
        colorDto.setId(colorId);
        return colorService.saveColor(colorDto);
    }

    public void deleteColorById(String colorId) throws ArchitectureException {
        checkNotNull(colorId);
        try {
            colorService.deleteColorById(colorId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CanNotDeleteException("color");
        }
    }

    private ColorDto checkNotNull(String colorId) throws EntityNotFoundException {
        ColorDto existColorDto = colorService.findColorById(colorId);
        if (existColorDto == null) {
            throw new EntityNotFoundException();
        }
        return existColorDto;
    }
}
