package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.CategoryFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.CategoryDto;
import apishop.model.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static apishop.util.api.ConstantsApi.Category.CATEGORY_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(CATEGORY_PATH)
public class CategoryController {
    private final CategoryFacade categoryFacade;

    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDto categoryDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, categoryFacade.create(categoryDto),true);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable String id,
                                                 @RequestBody CategoryDto categoryDto) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK, categoryFacade.update(categoryDto, id),true);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") String categoryId) throws ArchitectureException{
        categoryFacade.delete(categoryId);
        return ResponseHandler.response(HttpStatus.OK,"update successfully!",true);
    }

    @GetMapping("/id/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable("categoryId") String categoryId) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,categoryFacade.findById(categoryId),true);
    }

    @GetMapping
    public ResponseEntity<Object> getCategoryByName(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,
                categoryFacade.findAllCategory(new SearchCriteria(page, size, columSort)),true);
    }
}
