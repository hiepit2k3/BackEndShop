package apishop.service;

import apishop.entity.Color;
import apishop.entity.Product;
import apishop.model.dto.ProductVariantDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductVariantService {
    Page<ProductVariantDto> findAll(SearchCriteria searchCriteria);

    ProductVariantDto findById(String id);
    List<ProductVariantDto> findAllByProductId(String id);

    List<ProductVariantDto> saveAll(List<ProductVariantDto> productVariantsDto,
                                    Product product,
                                    List<Color> colors,
                                    List<MultipartFile> imagesProductVariants) throws IOException;

    List<ProductVariantDto> updateAll(List<ProductVariantDto> productVariantsDto,
                                      Product product,
                                      List<Color> colors,
                                      List<MultipartFile> imagesProductVariants,
                                      boolean isUpdateImage) throws IOException;

    void deleteAll(List<String> productVariantIds);

    Optional<String> getId(String productVariantId);

}
