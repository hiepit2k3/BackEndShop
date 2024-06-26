package apishop.service.impl;

import apishop.entity.*;
import apishop.exception.core.ArchitectureException;
import apishop.facade.HashtagOfProductFacade;
import apishop.facade.ProductVariantFacade;
import apishop.model.dto.*;
import apishop.model.mapper.BrandMapper;
import apishop.model.mapper.CategoryMapper;
import apishop.model.mapper.ProductMapper;
import apishop.model.mapper.ProductVariantMapper;
import apishop.model.request.PVRequest;
import apishop.model.request.ProductDtoRequest;
import apishop.model.response.ProductResponse;
import apishop.repository.*;
import apishop.service.*;
import apishop.util.enums.Gender;
import apishop.util.enums.Season;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductVariantFacade productVariantFacade;
    private final HashtagOfProductFacade hashtagOfProductFacade;
    private final DiscountService discountService;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;
    private final ProductVariantMapper productVariantMapper;
    private final HashtagService hashtagService;
    private final ProductVariantRepository productVariantRepository;
    private final DiscountRepository discountRepository;
    private final HashtagOfProductRepository hashtagOfProductRepository;
    private final HashtagRepository hashtagRepository;
//    private final S3AmazonService s3AmazonService;

    /*
     *Find by id product
     */
    @Override
    public ProductDto findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productMapper::apply).orElse(null);
    }


    @Override
    public ProductIdentity findByProductId(String id) {
        // tìm product bằng id
        Product product = productRepository.findById(id).get();
        ProductDto productDto = productMapper.apply(product);
        List<ProductVariant> t = productVariantRepository.findAllByProductId(product.getId());
        // lây ra list productVariant
        List<ProductVariantDto> productVariantsDto =
                productVariantMapper.applyAll(t);
        List<HashtagDto> hashtagDtos = new ArrayList<>();
        List<HashtagOfProduct> hashtagOfProducts = hashtagOfProductRepository.findAllByProductId(id);
        for (HashtagOfProduct hash : hashtagOfProducts) {
            hashtagDtos.add(hashtagService.findHashtagById(hash.getHashtag().getId()));
        }
        // Lấy brand và category
        BrandDto brandDto = brandMapper.apply(product.getBrand());
        CategoryDto categoryDto = categoryMapper.apply(product.getCategory());

        ProductIdentity productIdentity = new ProductIdentity();
        // tìm discount bằng categoryId
        DiscountDto discountDto = discountService.findByCategoryIdIsActived(productDto.getCategoryId());
        Date date = new Date();
        // nếu discount có tồn tại và ngày đăng ký sau ngày hiện tại và ngày hết hạn trước ngày hiện tại
        if (discountDto != null
                && discountDto.getRegisterDate().after(date)
                && discountDto.getExpirationDate().before(date))
            productIdentity.setDiscount(discountDto.getDiscount());

        productIdentity.setProductDto(productDto);
        productIdentity.setProductVariantsDto(productVariantsDto);
        productIdentity.setHashtagDtos(hashtagDtos);
        productIdentity.setBrandDto(brandDto);
        productIdentity.setCategoryDto(categoryDto);

        return productIdentity;
    }

    @Override
    public Page<ProductResponse> findAllWithFilter(SearchCriteria searchCriteria) {
        Slice<ProductResponse> productList = productRepository.product(getPageable(searchCriteria));
        productList.getContent().parallelStream().forEach(productResponse -> {
            byte[] imageData = productResponse.getMain_image().getData();
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            productResponse.setImage(base64Image);

            List<ProductVariant> productVariants = productVariantRepository.findAllByProductId(productResponse.getId());

            Date date = new Date();
            Optional<Discount> discount = discountRepository.findByCategoryIdIsActived(productResponse.getId(), date);

            double productDiscount = discount.map(Discount::getDiscount).orElse(0.0);

            int totalQuantity = productVariants.parallelStream().mapToInt(ProductVariant::getQuantity).sum();
            double minPrice = productVariants.parallelStream().map(ProductVariant::getPrice).min(Double::compareTo).orElse(0.0);
            double maxPrice = productVariants.parallelStream().map(ProductVariant::getPrice).max(Double::compareTo).orElse(0.0);

            productResponse.setDiscount(productDiscount);
            productResponse.setQuantity(totalQuantity);
            productResponse.setMin_price(minPrice);
            productResponse.setMax_price(maxPrice);
        });
        long count  = productRepository.count();
        Page<ProductResponse> productPage = new PageImpl<>(productList.getContent(), getPageable(searchCriteria),count);
        return productPage;
    }


    @Override
    public ProductDtoRequest create(ProductDtoRequest productDtoRequest,
                                    Category category,
                                    Brand brand,
                                    List<MultipartFile> files) throws ArchitectureException, IOException {
        productDtoRequest.getProductDto().setMultipartFile(files.get(0));
        Product product = productRepository
                .save(productMapper
                        .applyToProduct(productDtoRequest.getProductDto(),
                                category,
                                brand));

        productDtoRequest.setProductDto(productMapper.apply(product));

        List<MultipartFile> imagesProductVariants = files.subList(1, files.size());
        // Lưu productVariant và image của chúng
        List<ProductVariantDto> productVariantsDto =
                productVariantFacade.createAll(productDtoRequest.getProductVariantsDto(),
                        imagesProductVariants,
                        product
                );

        productDtoRequest.setProductVariantsDto(productVariantsDto);

        // Lưu HashtagOfProduct
        List<HashtagOfProductDto> hashtagOfProductsDto =
                hashtagOfProductFacade.createAll(
                        productDtoRequest.getHashtagOfProductsDto(),
                        product,
                        true);

        productDtoRequest.setHashtagOfProductsDto(hashtagOfProductsDto);

        return productDtoRequest;
    }

    /*
     * Update a Product
     * @param id
     */

    @Transactional(rollbackFor = ArchitectureException.class)
    @Override
    public ProductDto update(List<PVRequest> pvRequest,
                             ProductDto productDto,
                             MultipartFile mainImage,
                             List<HashtagOfProductDto> hashtagOfProductsDto,
                             String productId,
                             Category category,
                             Brand brand) throws ArchitectureException, IOException {
        // Lưu product và image của nó
        Product product = productRepository
                .save(productMapper
                        .applyToProduct(productDto,
                                category,
                                brand));

        productVariantFacade.update(pvRequest, product);
        hashtagOfProductFacade.createAll(hashtagOfProductsDto, product, true);

        return productMapper.apply(product);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

}
