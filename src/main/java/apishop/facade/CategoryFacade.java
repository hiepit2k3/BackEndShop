package apishop.facade;

import apishop.entity.Category;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.CategoryDto;
import apishop.model.dto.SearchCriteria;
import apishop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryService categoryService;

    public CategoryDto findById(String categoryId) throws ArchitectureException {
        return checkNotNull(categoryId);
    }

    public CategoryDto checkNotNull(String categoryId) throws EntityNotFoundException {
        CategoryDto category = categoryService.findByCategoryId(categoryId);
        if (category == null) {
            throw new EntityNotFoundException();
        }
        return category;
    }

    public Page<CategoryDto> findAllCategory(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<CategoryDto> result = categoryService.findAllCategory(searchCriteria);
        if (result.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return result;
    }

    public CategoryDto create(CategoryDto categoryDto) throws ArchitectureException {
        if (categoryDto.getId() != null) {
            throw new IdMustBeNullException(Category.class.getSimpleName());
        }
        return categoryService.save(categoryDto);
    }

    public CategoryDto update(CategoryDto categoryDto,String id) throws ArchitectureException {
        checkNotNull(id);
        categoryDto.setId(id);
        return categoryService.save(categoryDto);
    }

    public void delete(String categoryId) throws ArchitectureException {
        checkNotNull(categoryId);
        try {
            categoryService.delete(categoryId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CanNotDeleteException("category");
        }
    }

}
