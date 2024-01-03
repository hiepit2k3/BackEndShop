package apishop.facade;

import apishop.entity.Category;
import apishop.entity.Discount;
import apishop.exception.common.DescriptionException;
import apishop.exception.common.ForeignKeyIsNotFound;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.DiscountDto;
import apishop.model.dto.SearchCriteria;
import apishop.repository.CategoryRepository;
import apishop.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountFacade {

    private final DiscountService discountService;
    private final CategoryRepository categoryRepository;

    public DiscountDto createDiscount(DiscountDto discountDto) throws ArchitectureException, IOException {
        if (discountDto.getId() != null) throw new IdMustBeNullException(Discount.class.getSimpleName());
        isExist(discountDto);
        discountDto.setIsUpdate(false);
        Optional<Category> category = categoryRepository.findById(discountDto.getCategoryId());
        if (category.isEmpty())
            throw new ForeignKeyIsNotFound("Category");
        return discountService.saveDiscount(discountDto, category.get());
    }

    public DiscountDto updateDiscount(String discountId, DiscountDto dto) throws ArchitectureException, IOException {
        checkNotNull(discountId);
        isExist(dto);
        dto.setId(discountId);
        dto.setIsUpdate(true);
        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        if (category.isEmpty())
            throw new ForeignKeyIsNotFound("Category");
        return discountService.saveDiscount(dto, category.get());
    }

    private void isExist(DiscountDto dto) throws ArchitectureException {
        List<DiscountDto> discountsDto = discountService.findByCategoryId(dto.getCategoryId());
        for (DiscountDto discountDto : discountsDto) {
            if (discountDto != null &&
                    discountDto.getExpirationDate().getTime() >= dto.getRegisterDate().getTime()) {
                throw new DescriptionException("Discount is already exist in this time");
            }
        }
    }

    // vì lặp lại nhiều lần nên tạo ra hàm checkNotNull
    private DiscountDto checkNotNull(String discountId) throws EntityNotFoundException {
        DiscountDto existDiscountDto = discountService.findByDiscountId(discountId);
        if (existDiscountDto == null) {
            throw new EntityNotFoundException();
        }
        return existDiscountDto;
    }


    public void deleteDiscountById(String discountId) throws ArchitectureException {
        checkNotNull(discountId);
        discountService.deleteById(discountId);
    }

    public DiscountDto findDiscountById(String discountId) throws ArchitectureException {
        return checkNotNull(discountId);
    }

    public Page<DiscountDto> findAll(SearchCriteria searchCriteria) throws ArchitectureException {
        //Checking if user is already exist
        Page<DiscountDto> listDiscounts = discountService.findAll(searchCriteria);
        if (listDiscounts.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return listDiscounts;
    }

    public List<DiscountDto> findAllIsActived() throws ArchitectureException {
        List<DiscountDto> listDiscounts = discountService.findAllIsActived();
        if (listDiscounts.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return listDiscounts;
    }
}
