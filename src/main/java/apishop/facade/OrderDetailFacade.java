package apishop.facade;

import apishop.entity.Order;
import apishop.entity.OrderDetail;
import apishop.entity.ProductVariant;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.OrderDetailDto;
import apishop.model.dto.SearchCriteria;
import apishop.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailFacade {
    private final OrderDetailService orderDetailService;

    public Page<OrderDetailDto> findAll(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<OrderDetailDto> listOrderDetails = orderDetailService.findAllOrderDetail(searchCriteria);
        if (listOrderDetails.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return listOrderDetails;
    }

    public List<OrderDetailDto> findAllByOrderId(String orderId) throws ArchitectureException {
        List<OrderDetailDto> listOrderDetails = orderDetailService.findAllByOrderId(orderId);
        if (listOrderDetails.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return listOrderDetails;
    }


    public List<OrderDetailDto> saveAll(List<OrderDetailDto> orderDetailsDto,
                                        List<ProductVariant> productVariants,
                                        Order order
    ) throws ArchitectureException {
        // Kiểm tra list
        if (orderDetailsDto.isEmpty())
            throw new InvalidParamException();
        // Kiểm tra có id không
        for (OrderDetailDto orderDetailDto : orderDetailsDto) {
            if (orderDetailDto.getId() != null)
                throw new IdMustBeNullException(OrderDetail.class.getSimpleName());
        }
        // Lưu
        List<OrderDetailDto> list =
                orderDetailService.saveAll(orderDetailsDto, productVariants, order);
        // Kiểm tra có lưu thành công
        if (list.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return list;
    }
}
