package apishop.service.impl;

import apishop.entity.Category;
import apishop.model.dto.CategoryDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.CategoryMapper;
import apishop.repository.CategoryRepository;
import apishop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public Page<CategoryDto> findAllCategory(SearchCriteria searchCriteria){
        Page<Category> result = categoryRepository.findAll(getPageable(searchCriteria));
        return result.map(categoryMapper::apply);
    }
    /*
     * find category by id
     * param id
     * return category
     */

    @Override
    public CategoryDto findByCategoryId(String id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(categoryMapper::apply).orElse(null);
    }

    /*
     * find category by name
     * param name
     * return category
     */


    /*
     * create a new category
     * return new category
     */
    @Override
    public CategoryDto save(CategoryDto categoryDto){
        return categoryMapper.apply(categoryRepository.save(categoryMapper.applyToCategory(categoryDto)));
    }

    /*
     * update a category
     * return update category
     */



    /*
     * delete a category
     * param id
     */

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }
}
