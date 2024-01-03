package apishop.service;

import apishop.entity.Order;
import apishop.entity.ProductVariant;
import apishop.model.dto.OrderDetailDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderDetailService {
    Page<OrderDetailDto> findAllOrderDetail(SearchCriteria searchCriteria);

    List<OrderDetailDto> findAllByOrderId(String orderId);



    List<OrderDetailDto> saveAll(List<OrderDetailDto> orderDetailDtoList,
                                 List<ProductVariant> productVariantList,
                                 Order order);
}
