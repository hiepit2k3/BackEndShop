package apishop.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderIdentity {
    private OrderDto orderDto;
    private DeliveryAddressDto deliveryAddressDto;
    private PaymentDto paymentDto;
    private VoucherDto voucherDto;
    private AccountDto accountDto;
    private List<OrderDetailDto> orderDetailsDto;

}
