package apishop.service;

import apishop.entity.Brand;
import apishop.entity.Category;
import apishop.exception.core.ArchitectureException;
import apishop.model.dto.HashtagOfProductDto;
import apishop.model.dto.ProductDto;
import apishop.model.dto.ProductIdentity;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.PVRequest;
import apishop.model.request.ProductDtoRequest;
import apishop.model.response.ProductResponse;
import apishop.util.enums.Gender;
import apishop.util.enums.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public interface ProductService {
    /*
    *Find by id product
     */
    ProductDto findById(String id);

    ProductIdentity findByProductId(String id);

    Page<ProductResponse>
        findAllWithFilter(
                          SearchCriteria searchCriteria);

    ProductDtoRequest create(ProductDtoRequest productDtoRequest,
                             Category category,
                             Brand brand,
                             List<MultipartFile> files) throws ArchitectureException, IOException;

    ProductDto update(List<PVRequest> pvRequest,
                             ProductDto productDto,
                             MultipartFile mainImage,
                             List<HashtagOfProductDto> hashtagOfProductDtos,
                             String productId,
                             Category category,
                             Brand brand) throws ArchitectureException, IOException;

    void delete(String id);
}
