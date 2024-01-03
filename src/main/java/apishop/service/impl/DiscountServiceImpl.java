package apishop.service.impl;

import apishop.entity.Category;
import apishop.entity.Discount;
import apishop.model.dto.DiscountDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.DiscountMapper;
import apishop.repository.DiscountRepository;
import apishop.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    @Override
    public DiscountDto saveDiscount(DiscountDto discountDto, Category category) throws IOException {
        if(discountDto.getIsUpdate()){
            Optional<Discount> discount = discountRepository.findById(discountDto.getId());
            discountDto.setImage(Base64.getEncoder().encodeToString(discount.get().getImage().getData()));
        }
        Discount discount = discountMapper.applyToDiscount(discountDto, category);
/*
    Giải thích: client gửi cho server 1 đối tượng dto để lưu
    => cần chuyển qua entity mới có thể lưu vào database
    => và trả về là 1 dto
    => nếu thêm mới thì id = null
    => nên cần tạo 1 dto mới hứng 1 entity trả về sau khi lưu
    => id khi đó sẽ khác null
    => đoạn trên có thể viết gọn thành
    => return discountMapper.apply(discountRepository.save(discountMapper.applyToDiscount(discountDto)));
 */
        return discountMapper.apply(discountRepository.save(discount));
    }

    @Override
    public void deleteById(String discountId) {
        discountRepository.deleteById(discountId);
    }

    @Override
    public DiscountDto findByDiscountId(String discountId){
        Optional<Discount> customer = discountRepository.findById(discountId);
// Vì có nhiều lớp dùng đến service này nên trươn hợp không tìm sẽ bắn ra null thay vì auto map
        if(customer.isPresent())
            return discountMapper.apply(customer.get());

        return null;
    }

    @Override
    public Page<DiscountDto> findAll(SearchCriteria searchCriteria){
        Page<Discount> discounts = discountRepository.findAll(getPageable(searchCriteria));
        return discounts.map(discountMapper::apply);
    }

    @Override
    public DiscountDto findByCategoryIdIsActived(String categoryId) {
        Date date = new Date();
        Optional<Discount> discount = discountRepository.findByCategoryIdIsActived(categoryId,date);
        return discount.map(discountMapper::apply).orElse(null);
    }

    @Override
    public List<DiscountDto> findByCategoryId(String categoryId) {
        List<Discount> discounts = discountRepository.findByCategoryId(categoryId);
        return discounts.stream().map(discountMapper::apply).toList();
    }

    @Override
    public List<DiscountDto> findAllIsActived() {
        Date date = new Date();
        List<Discount> discounts = discountRepository.findAllIsActived(date);
        return discounts.stream().map(discountMapper::apply).toList();
    }
}
