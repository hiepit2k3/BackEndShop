package apishop.facade;

import apishop.entity.Brand;
import apishop.entity.Category;
import apishop.entity.Product;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.ForeignKeyIsNotFound;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.HashtagOfProductDto;
import apishop.model.dto.ProductDto;
import apishop.model.dto.ProductIdentity;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.PVRequest;
import apishop.model.request.ProductDtoRequest;
import apishop.model.response.ProductResponse;
import apishop.repository.BrandRepository;
import apishop.repository.CategoryRepository;
import apishop.service.ProductService;
import apishop.util.enums.Gender;
import apishop.util.enums.Season;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public Slice<ProductResponse> findAllWithFilter(String categoryId,
                                                    String brandId,

                                                    SearchCriteria searchCriteria) throws ArchitectureException {
        Slice<ProductResponse> page =
                productService. findAllWithFilter( searchCriteria);
        if (page.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return page;
    }

    public ProductIdentity findByProductId(String productId) throws ArchitectureException {
        checkNotNull(productId);
        return productService.findByProductId(productId);
    }

    public ProductDto checkNotNull(String productDtoId) throws EntityNotFoundException {
        ProductDto productDto = productService.findById(productDtoId);
        if (productDto == null) {
            throw new EntityNotFoundException();
        }
        return productDto;
    }

    public ProductDto update(List<PVRequest> pvRequest,
                                    ProductDto productDto,
                                    MultipartFile mainImage,
                                    List<HashtagOfProductDto> hashtagOfProductDtos,
                                    String productId) throws ArchitectureException, IOException {

        checkNotNull(productId);
        System.out.println(mainImage);
        productDto.setId(productId);
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        Optional<Brand> brand = brandRepository.findById(productDto.getBrandId());
        // check category is exist
        if (category.isEmpty() || brand.isEmpty())
            throw new ForeignKeyIsNotFound("CategoryId or Brand");

        return productService.update(pvRequest,
                                     productDto,
                                     mainImage,
                                     hashtagOfProductDtos,
                                     productId,
                                     category.get(),
                                     brand.get());
    }

    public void deleteById(String id) throws ArchitectureException {
        checkNotNull(id);
        try {
            productService.delete(id);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CanNotDeleteException("product");
        }
    }

    public ProductDtoRequest create(ProductDtoRequest productDtoRequest,
                                    List<MultipartFile> files) throws ArchitectureException, IOException {

        if (productDtoRequest.getProductVariantsDto().isEmpty() ||
            productDtoRequest.getHashtagOfProductsDto().isEmpty() ||
            files.isEmpty())
            throw new InvalidParamException();

        Optional<Category> category = categoryRepository.findById(productDtoRequest.getProductDto().getCategoryId());
        Optional<Brand> brand = brandRepository.findById(productDtoRequest.getProductDto().getBrandId());
        // check category is exist
        if (category.isEmpty() || brand.isEmpty())
            throw new ForeignKeyIsNotFound("CategoryId or Brand");
        // id product must be null
        if (productDtoRequest.getProductDto().getId() != null)
            throw new IdMustBeNullException(Product.class.getSimpleName());

        return productService.create(productDtoRequest,
                                    category.get(),
                                    brand.get(),
                                    files);
    }
}

