package apishop.service;

import apishop.entity.*;
import apishop.exception.core.ArchitectureException;
import apishop.model.dto.OrderDto;
import apishop.model.dto.OrderIdentity;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.OrderDtoRequest;
import apishop.util.enums.TypeOrder;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface OrderService {

    Page<OrderDtoRequest> findAllByAccountIdAndTypeOrder(String accountId,
                                                         TypeOrder typeOrder,
                                                         SearchCriteria searchCriteria);


    OrderDto saveOrder(Order order) throws MessagingException;

    Page<OrderDtoRequest> findAllByTypeOrder(
            TypeOrder typeOrder,
            SearchCriteria searchCriteria);

    OrderDtoRequest save(OrderDtoRequest orderDtoRequest,
                         Payment payment,
                         Account account,
                         Voucher voucher,
                         DeliveryAddress deliveryAddress) throws ArchitectureException, MessagingException;

    OrderIdentity findByIdAndAccount(String accountId, String orderId) throws ArchitectureException;

    OrderDto findOrderById(String id);

    List<OrderDto> findAllByDeliveryAddressId(String deliveryAddressId);


}
