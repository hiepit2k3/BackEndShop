package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.ProductFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.HashtagOfProductDto;
import apishop.model.dto.ProductDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.PVRequest;
import apishop.model.request.ProductDtoRequest;
import apishop.util.enums.Gender;
import apishop.util.enums.Season;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static apishop.util.api.ConstantsApi.Product.PRODUCT_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCT_PATH)
public class ProductController {
    private final ProductFacade productFacade;
    private final ObjectMapper objectMapper;

    /**
     * Only admin can access
     */
    @PostMapping("/create")
    public ResponseEntity<Object> create(String productDtoRequest, List<MultipartFile> files) throws ArchitectureException, IOException, ArchitectureException {
        ProductDtoRequest productDtoRequestConvert =
                objectMapper.readValue(productDtoRequest, ProductDtoRequest.class);

        return ResponseHandler.response(HttpStatus.OK,
                productFacade.create(productDtoRequestConvert, files), true);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Object> update(PVRequest pvRequest,
                                         String productDto,
                                         MultipartFile mainImage,
                                         String hashtagOfProducts,
                                         @PathVariable("productId") String productId) throws ArchitectureException, IOException {
        ProductDto productDtoConvert = objectMapper.readValue(productDto, ProductDto.class);
        List<HashtagOfProductDto> hashtagOfProductsConvert =
                objectMapper.readValue(hashtagOfProducts, new TypeReference<>() {});

        return ResponseHandler.response(HttpStatus.OK, productFacade.update(pvRequest.getPvRequests(),
                                                                            productDtoConvert,
                                                                            mainImage,
                                                                            hashtagOfProductsConvert,
                                                                            productId), true);
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<Object> getProductByProductId(@PathVariable("productId") String productId) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,productFacade.findByProductId(productId),true);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Object> deleteProductByproductId(@PathVariable("productId") String productId) throws ArchitectureException{
        productFacade.deleteById(productId);
        return ResponseHandler.response(HttpStatus.OK,"Delete successfully",true);
    }

    @GetMapping
    public ResponseEntity<Object> getAllProduct(
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String brandId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,
                productFacade.findAllWithFilter(categoryId, brandId,
                        new SearchCriteria(page, size, columSort)), true);
    }
}
