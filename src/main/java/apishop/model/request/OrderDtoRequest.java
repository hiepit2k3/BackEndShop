package apishop.model.request;

import apishop.model.dto.AccountDto;
import apishop.model.dto.OrderDetailDto;
import apishop.model.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderDtoRequest {
    @NonNull
    private OrderDto orderDto;
    private AccountDto accountDto;
    @NonNull
    private List<OrderDetailDto> orderDetailsDto;
}
