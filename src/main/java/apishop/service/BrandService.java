package apishop.service;

import apishop.model.dto.BrandDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface BrandService {
    Page<BrandDto> findAll(SearchCriteria searchCriteria);

    BrandDto findById(String brandId);

    Page<BrandDto> findByName(String name, SearchCriteria searchCriteria);
    BrandDto save(BrandDto branchDto) throws IOException;

    void delete(String branchId);
}
