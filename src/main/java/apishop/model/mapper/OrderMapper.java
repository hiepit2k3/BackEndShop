package apishop.model.mapper;


import apishop.entity.*;
import apishop.model.dto.OrderDto;
import apishop.util.enums.TypeOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderMapper implements Function<Order, OrderDto> {

    @Override
    public OrderDto apply(Order order) {
        return OrderDto
                .builder()
                .id(order.getId())
                .purchaseDate(order.getPurchaseDate())
                .typeOrder(order.getTypeOrder())
                .total(order.getTotal())
                .deliveryAddressId(order.getDeliveryAddress().getId())
                .paymentId(order.getPayment().getId())
                .accountId(order.getAccount().getId())
                .voucherId(order.getVoucher() == null ? null : order.getVoucher().getId())
                .build();
    }

    public Order applyToOrder(OrderDto orderDto,
                              Payment payment,
                              Account account,
                              Voucher voucher ,
                              DeliveryAddress deliveryAddress) {
        return Order
                .builder()
                .id(orderDto.getId())
                .total(orderDto.getTotal())
                .deliveryAddress(deliveryAddress)
                .payment(payment)
                .account(account)
                .voucher(voucher)
                .purchaseDate(orderDto.getPurchaseDate() == null
                        ? new Date(System.currentTimeMillis())
                        : orderDto.getPurchaseDate())
                .typeOrder(payment.getName().contains("VN Pay")
                        ? TypeOrder.WAIT_TO_PAY
                        : orderDto.getTypeOrder() == null
                        ? TypeOrder.PENDING
                        : orderDto.getTypeOrder())
                .build();
    }
}
