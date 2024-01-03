package apishop.service;

import apishop.entity.Order;
import apishop.model.dto.OrderDetailDto;
import apishop.util.enums.TypeOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThymeleafService {

    String getVerifyEmailContent(String token, String api);

    String getWelcomeEmailContent(String username);

    String getChangePassEmailContent(String username);

    String getOrderEmailContent(String email,
                                Order order,
                                List<OrderDetailDto> orderDetailsDto,
                                Double discount);

    String updateOrderEmailContent(String email,
                                   String orderId,
                                   TypeOrder typeOrder);
}
