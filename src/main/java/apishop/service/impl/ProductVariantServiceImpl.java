package apishop.service.impl;

import apishop.entity.Color;
import apishop.entity.Product;
import apishop.entity.ProductVariant;
import apishop.model.dto.ProductVariantDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.ProductVariantMapper;
import apishop.repository.ProductVariantRepository;
import apishop.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantMapper productVariantMapper;
//    private final S3AmazonService s3AmazonService;


    /*
     * find all product variants and page
     */
    @Override
    public Page<ProductVariantDto> findAll(SearchCriteria searchCriteria) {
        Page<ProductVariant> productVariants = productVariantRepository.findAll(getPageable(searchCriteria));
        return productVariants.map(productVariantMapper);
    }

    @Override
    public ProductVariantDto findById(String id) {
        return productVariantRepository.findById(id)
                .map(productVariantMapper)
                .orElseThrow();
    }

    /*
     * find all productVariants by id product and page
     */


    @Override
    public List<ProductVariantDto> findAllByProductId(String id) {
        return productVariantRepository.findAllByProductId(id).stream()
                .map(productVariantMapper)
                .toList();
    }

    /*
     *  Phương thức này sẽ chuyển dto thành entity
     *  dựa trên product và list color
     *  sau đó lưu vào database
     *  và trả về list productVariantDto chứa hình của chúng
     * */
//    @Override
    public List<ProductVariantDto> saveAll(List<ProductVariantDto> productVariantsDto,
                                           Product product,
                                           List<Color> colors,
                                           List<MultipartFile> imagesProductVariants) throws IOException {
        for (int i = 0; i < productVariantsDto.size(); i++) {
            MultipartFile image = imagesProductVariants.get(i);
            productVariantsDto.get(i).setMultipartFile(image);
        }
        List<ProductVariant> productVariantsNew =
                productVariantRepository.saveAll(
                        productVariantMapper.applyAllToProductVariant(productVariantsDto, product, colors)
                );

        productVariantsDto.clear();

        productVariantsDto.addAll(productVariantsNew.stream()
                .map(productVariantMapper)
                .toList());

        return productVariantsDto;

    }

    @Override
    public List<ProductVariantDto> updateAll(List<ProductVariantDto> productVariantsDto,
                                             Product product,
                                             List<Color> colors,
                                             List<MultipartFile> imagesProductVariants,
                                             boolean isChangeImage) throws IOException {
        if (isChangeImage){
            for (int i=0; i<productVariantsDto.size(); i++){
                productVariantsDto.get(i).setMultipartFile(imagesProductVariants.get(i));
            }
        }
        List<ProductVariant> productVariantsNew =
                productVariantRepository.saveAll(
                        productVariantMapper.applyAllToProductVariant(productVariantsDto, product, colors)
                );

        productVariantsDto.clear();

        productVariantsDto.addAll(productVariantsNew.stream()
                                                    .map(productVariantMapper)
                                                    .toList());
        return productVariantsDto;
    }

    @Override
    public void deleteAll(List<String> productVariantIds) {
        productVariantRepository.deleteAllById(productVariantIds);
    }

    @Override
    public Optional<String> getId(String productVariantId) {
        return productVariantRepository.getId(productVariantId);
    }

}
