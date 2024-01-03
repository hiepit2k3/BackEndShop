package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.BrandFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.BrandDto;
import apishop.model.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static apishop.util.api.ConstantsApi.Brand.BRAND_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(BRAND_PATH)
public class BrandController {

    private final BrandFacade brandFacade;

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                brandFacade.findAll(new SearchCriteria(page, size, columSort)),
                true
        );
    }

    @GetMapping("/id/{brandId}")
    public ResponseEntity<Object> findBrandById(@PathVariable String brandId) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                brandFacade.findById(brandId),
                true
        );
    }

    @GetMapping("/name/{brandName}")
    public ResponseEntity<Object> findAllByBrandName(
            @PathVariable String brandName,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") Integer size,
        @RequestParam(defaultValue = "id") String columSort )
        throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                brandFacade.findByBrandName(brandName, new SearchCriteria(page, size, columSort)),
                true
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createBrand(BrandDto brandDto) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK,
                brandFacade.create(brandDto), true);

    }

    @PutMapping("/update/{brandId}")
    public ResponseEntity<Object> updateBrand(BrandDto brandDto, @PathVariable String brandId) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK,
                brandFacade.update(brandDto, brandId), true);
    }

    @DeleteMapping("/delete/{brandId}")
    public ResponseEntity<Object> deleteBrand(@PathVariable String brandId) throws ArchitectureException {
        brandFacade.delete(brandId);
        return ResponseHandler.response(HttpStatus.OK, "Delete successfully", true);
    }

}

