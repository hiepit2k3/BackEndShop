package apishop.facade;

import apishop.entity.Color;
import apishop.entity.Product;
import apishop.entity.ProductVariant;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.DescriptionException;
import apishop.exception.common.ForeignKeyIsNotFound;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.ProductVariantDto;
import apishop.model.request.PVRequest;
import apishop.repository.ColorRepository;
import apishop.repository.ProductVariantRepository;
import apishop.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductVariantFacade {
    private final ProductVariantService productVariantService;
    private final ColorRepository colorRepository;
    private final ProductVariantRepository productVariantRepository;


    public ProductVariantDto findById(String id) throws ArchitectureException {
        if (id == null) {
            throw new InvalidParamException();
        }
        return checkNotNull(id);
    }


    public ProductVariantDto checkNotNull(String id) throws EntityNotFoundException {
        ProductVariantDto productVariantDto = productVariantService.findById(id);
        if (productVariantDto == null) {
            throw new EntityNotFoundException();
        }
        return productVariantDto;
    }

    public List<ProductVariantDto> createAll(List<ProductVariantDto> productVariantsDto,
                                             List<MultipartFile> imagesProductVariants,
                                             Product product
    ) throws ArchitectureException, IOException {

        checkPV(productVariantsDto);

        if (productVariantsDto.isEmpty()) throw new InvalidParamException();

        for (ProductVariantDto productVariantDto : productVariantsDto) {
            productVariantDto.setId(null);
        }

        return productVariantService.saveAll(productVariantsDto,
                                            product,
                                            getColor(productVariantsDto),imagesProductVariants);
    }

    public void update(List<PVRequest> pvRequests, Product product) throws ArchitectureException, IOException {

        checkPV(pvRequests.stream().map(PVRequest::getData).toList());

        List<ProductVariantDto> createPV = new ArrayList<>();
        List<MultipartFile> createFiles = new ArrayList<>();
        List<ProductVariantDto> updatePVNonImage = new ArrayList<>();
        List<ProductVariantDto> updatePV = new ArrayList<>();
        List<MultipartFile> updateFiles = new ArrayList<>();
        List<ProductVariantDto> oldPVD = productVariantService.findAllByProductId(product.getId());
        List<String> oldPV = oldPVD.stream().map(ProductVariantDto::getId).toList();

        for (PVRequest pvRequest : pvRequests) {

            /** Trường hợp không có id => tạo mới**/
            if (pvRequest.getData().getId() == null) {
                if (pvRequest.getImageFile().isEmpty())
                    throw new InvalidParamException();
                createPV.add(pvRequest.getData());
                createFiles.add(pvRequest.getImageFile());
            }

            /** Trường hợp có id **/
            else {
                for (ProductVariantDto pv  : oldPVD) {

                    /** id giống với id cũ và có hình  => cập nhật**/
                    if (pv.getId().equals(pvRequest.getData().getId())
                            && pvRequest.getImageFile() != null
                    ) {
                        pvRequest.getData().setImage(pv.getImage());
                        updatePV.add(pvRequest.getData());
                        updateFiles.add(pvRequest.getImageFile());

                    } /** id giống với id cũ và không có hình  => cập nhật non image**/
                    else if (pv.getId().equals(pvRequest.getData().getId())) {
                        pvRequest.getData().setImage(pv.getImage());
                        updatePVNonImage.add(pvRequest.getData());
                    }
                }
            }
        }

        productVariantService.saveAll(createPV, product, getColor(createPV), createFiles);

        productVariantService.updateAll(updatePVNonImage, product, getColor(updatePVNonImage), null, false);

        productVariantService.updateAll(updatePV, product, getColor(updatePV), updateFiles, true);

        getListDelete(pvRequests, oldPV);
    }


    public List<Color> getColor(List<ProductVariantDto> productVariantsDto) throws ForeignKeyIsNotFound {
        List<Color> colors = new ArrayList<>();
        for (ProductVariantDto variant : productVariantsDto) {
            Optional<Color> color = colorRepository.findById(variant.getColorId());
            if (color.isEmpty())
                throw new ForeignKeyIsNotFound(Color.class.getSimpleName());
            colors.add(color.get());
        }
        return colors;

    }

    public ProductVariant getEntity(String id) throws EntityNotFoundException {
        Optional<ProductVariant> productVariant = productVariantRepository.findById(id);
        if (productVariant.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return productVariant.get();
    }

    public void getListDelete(List<PVRequest> pvRequests, List<String> oldPVId) throws CanNotDeleteException {
        List<String> deleteId = new ArrayList<>();
        for (String id : oldPVId) {
            boolean isDelete = true;
            for (PVRequest pvRequest : pvRequests) {
                if (id.equals(pvRequest.getData().getId())) {
                    isDelete = false;
                    break;
                }
            }
            if (isDelete) deleteId.add(id);
        }
        try{
            productVariantService.deleteAll(deleteId);
        } catch (DataIntegrityViolationException e){
            throw new CanNotDeleteException("Product variant");
        }
    }

    public void checkPV(List<ProductVariantDto> productVariantDtos) throws DescriptionException {
        Set<String> productSet = new HashSet<>();

        for (ProductVariantDto pv : productVariantDtos) {
            String key = pv.getColorId() + "-" + pv.getSize();

            if (!productSet.add(key)) {
                // Nếu key đã tồn tại trong Set, có nghĩa là đã có sản phẩm trùng lặp
                throw new DescriptionException("Size and color is duplicate");
            }
        }
    }
}
