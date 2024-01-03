package apishop.facade;

import apishop.entity.Brand;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.BrandDto;
import apishop.model.dto.SearchCriteria;
import apishop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BrandFacade {
    private final BrandService brandService;
//    private final S3AmazonService s3AmazonService;

    public Page<BrandDto> findAll(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<BrandDto> page = brandService.findAll(searchCriteria);
        if (page.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return page;
    }

    public BrandDto findById(String brandId) throws ArchitectureException {
        return checkNotNull(brandId);
    }

    public Page<BrandDto> findByBrandName(String brandName, SearchCriteria searchCriteria) throws ArchitectureException {
        Page<BrandDto> page = brandService.findByName(brandName, searchCriteria);
        if (page.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return page;
    }

    public BrandDto create(BrandDto brandDto) throws ArchitectureException, IOException {
        if (brandDto.getId() != null) {
            throw new IdMustBeNullException(Brand.class.getSimpleName());
        }
        brandDto.setIsUpdate(false);
        return brandService.save(brandDto);
    }

    public BrandDto update(BrandDto brandDto, String id) throws ArchitectureException, IOException {
        checkNotNull(id);
        brandDto.setId(id);
        brandDto.setIsUpdate(true);
        return brandService.save(brandDto);
    }

    public void delete(String brandDtoId) throws ArchitectureException {
        checkNotNull(brandDtoId);
        try {
            brandService.delete(brandDtoId);
        } catch (DataIntegrityViolationException e) {
            throw new CanNotDeleteException("brand");
        }
    }

    public BrandDto checkNotNull(String brandDtoId) throws EntityNotFoundException {
        BrandDto brandDto = brandService.findById(brandDtoId);
        if (brandDto == null) {
            throw new EntityNotFoundException();
        }
        return brandDto;
    }

}
