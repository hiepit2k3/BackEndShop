package apishop.service;


import apishop.entity.Order;
import apishop.model.dto.OrderDetailDto;
import apishop.util.enums.TypeOrder;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MailService {

    void sendVerifyEmail(String receiverEmail, String token, String api) throws MessagingException;

    void sendWelcomeEmail(String receiverEmail, String username) throws MessagingException;

    void sendOrderEmail(String receiverEmail,
                        Order order,
                        List<OrderDetailDto> orderDetailsDto,
                        Double discount) throws MessagingException;

    void sendChangePassEmail(String receiverEmail, String username) throws MessagingException;


    void sendUpdateOrderEmail(String receiverEmail, String orderId, TypeOrder typeOrder) throws MessagingException;
}
