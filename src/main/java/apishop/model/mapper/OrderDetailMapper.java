package apishop.model.mapper;

import apishop.entity.Order;
import apishop.entity.OrderDetail;
import apishop.entity.ProductVariant;
import apishop.model.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderDetailMapper implements Function<OrderDetail, OrderDetailDto> {


    @Override
    public OrderDetailDto apply(OrderDetail orderDetail) {
        return OrderDetailDto
                .builder()
                .id(orderDetail.getId())
                .name(orderDetail.getName())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .image(orderDetail.getImage())
                .size(orderDetail.getSize())
                .color(orderDetail.getColor())
                .discount(orderDetail.getDiscount())
                .orderId(orderDetail.getOrder().getId())
                .productVariantId(orderDetail.getProductVariant().getId())
                .productId(orderDetail.getProductVariant().getProduct().getId())
                .isEvaluate(orderDetail.getIsEvaluate())
                .build();
    }

    public OrderDetail applyToOrderDetail(OrderDetailDto orderDetailDto,
                                          ProductVariant productVariant,
                                          Order order) {
        return OrderDetail
                .builder()
                .id(orderDetailDto.getId())
                .name(orderDetailDto.getName())
                .quantity(orderDetailDto.getQuantity())
                .price(orderDetailDto.getPrice())
                .image(orderDetailDto.getImage())
                .size(orderDetailDto.getSize())
                .color(orderDetailDto.getColor())
                .discount(orderDetailDto.getDiscount())
                .isEvaluate(orderDetailDto.getIsEvaluate() != null && orderDetailDto.getIsEvaluate())
                .order(order)
                .productVariant(productVariant)
                .build();
    }

    public List<OrderDetail> applyAllToOrderDetail(
            List<OrderDetailDto> orderDetailDtoList,
            List<ProductVariant> productVariantList,
            Order order)
    {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (int i = 0; i < orderDetailDtoList.size(); i++) {
            orderDetailList.add(
                    applyToOrderDetail(
                            orderDetailDtoList.get(i),
                            productVariantList.get(i),
                            order));
        }
        return orderDetailList;
    }

    public List<OrderDetailDto> applyAll(List<OrderDetail> orderDetailList) {
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetailDtoList.add(apply(orderDetail));
        }
        return orderDetailDtoList;
    }

}
