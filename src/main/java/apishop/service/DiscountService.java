package apishop.service;

import apishop.entity.Category;
import apishop.model.dto.DiscountDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface DiscountService {

    DiscountDto saveDiscount(DiscountDto discountDto, Category category) throws IOException;
// Save này bao gồm cả update và create
    void deleteById(String discountId);

    DiscountDto findByDiscountId(String discountId);

    Page<DiscountDto> findAll(SearchCriteria searchCriteria);

    DiscountDto findByCategoryIdIsActived(String categoryId);

    List<DiscountDto> findByCategoryId(String categoryId);

    List<DiscountDto> findAllIsActived();
}
