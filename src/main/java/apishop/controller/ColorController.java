package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.ColorFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.ColorDto;
import apishop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static apishop.util.api.ConstantsApi.Color.COLOR_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(COLOR_PATH)
public class ColorController {
    private final ColorFacade colorFacade;

    @GetMapping
    public ResponseEntity<Object> getAll() throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                colorFacade.findAll(),
                true);
    }

    @GetMapping("/id/{colorId}")
    public ResponseEntity<Object> findColorById(@PathVariable String colorId) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                colorFacade.findColorById(colorId),
                true
        );
    }


    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ColorDto colorDto){
        return ResponseHandler.response(HttpStatus.OK,colorFacade.createColor(colorDto),true);
    }

    @PutMapping("/update/{colorId}")
    public ResponseEntity<Object> updateColor(@RequestBody ColorDto colorDto, @PathVariable String colorId) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, colorFacade.updateColor(colorId, colorDto), true);
    }

    @DeleteMapping("/delete/{colorId}")
    public ResponseEntity<Object> deleteColor(@PathVariable String colorId) throws ArchitectureException {
        colorFacade.deleteColorById(colorId);
        return ResponseHandler.response(HttpStatus.OK, "Delete successfully", true);
    }

}
