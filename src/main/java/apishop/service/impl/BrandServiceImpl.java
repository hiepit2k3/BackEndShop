package apishop.service.impl;

import apishop.entity.Brand;
import apishop.model.dto.BrandDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.BrandMapper;
import apishop.repository.BrandRepository;
import apishop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Page<BrandDto> findAll(SearchCriteria searchCriteria) {
        Page<Brand> result = brandRepository.findAll(getPageable(searchCriteria));
        return result.map(brandMapper::apply);
    }

    @Override
    public BrandDto findById(String brandId) {
        Optional<Brand> branch = brandRepository.findById(brandId);
        return branch.map(brandMapper::apply).orElse(null);
    }

    @Override
    public Page<BrandDto> findByName(String name, SearchCriteria searchCriteria) {
        Page<Brand> result = brandRepository.findAllByNameContains(name, getPageable(searchCriteria));
        return result.map(brandMapper::apply);
    }

    @Override
    public BrandDto save(BrandDto branchDto) throws IOException {
        if(branchDto.getIsUpdate()) {
            Optional<Brand> branch = brandRepository.findById(branchDto.getId());
            branchDto.setImage(Base64.getEncoder().encodeToString(branch.get().getImage().getData()));
        }
        return brandMapper.apply(brandRepository.save((brandMapper.applyToBrand(branchDto))));
    }

    @Override
    public void delete(String branchId){
        brandRepository.deleteById(branchId);
    }
}
