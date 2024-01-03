package apishop.service;


import apishop.model.dto.CategoryDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<CategoryDto> findAllCategory(SearchCriteria searchCriteria);

    CategoryDto findByCategoryId(String id);

    CategoryDto save(CategoryDto categoryDto);


    void delete(String id);
}
