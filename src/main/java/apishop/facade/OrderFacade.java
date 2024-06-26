package apishop.facade;

import apishop.entity.*;
import apishop.exception.common.DescriptionException;
import apishop.exception.common.ForeignKeyIsNotFound;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.core.OrderProcessor;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.OrderDto;
import apishop.model.dto.OrderIdentity;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.OrderDtoRequest;
import apishop.repository.DeliveryAddressRepository;
import apishop.repository.OrderRepository;
import apishop.repository.PaymentRepository;
import apishop.repository.VoucherRepository;
import apishop.service.OrderService;
import apishop.service.VnPayService;
import apishop.util.enums.Role;
import apishop.util.enums.TypeOrder;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static apishop.util.method.Authentication.getAccount;
import static apishop.util.vnpay.ConstantsVNPay.URL_RETURN_FRONTEND;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final VoucherRepository voucherRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final OrderRepository orderRepository;
    private final VnPayService vnPayService;
    private final OrderProcessor orderProcessor;

    public Page<OrderDtoRequest> findByAllOrders(SearchCriteria searchCriteria,
                                                 TypeOrder typeOrder) throws ArchitectureException {
        Page<OrderDtoRequest> listOrders;
        Account account = getAccount();
        if (account.getRole() == Role.CUSTOMER)
            listOrders = orderService.findAllByAccountIdAndTypeOrder(account.getId(),
                    typeOrder,
                    searchCriteria);
        else
            listOrders = orderService.findAllByTypeOrder(typeOrder,searchCriteria);
        System.out.println(listOrders);
        if(listOrders.isEmpty()){
            throw new EntityNotFoundException();
        }
        return listOrders;
    }

    public OrderIdentity findOrderById(String orderId) throws ArchitectureException {
        Account account = getAccount();
        OrderIdentity dto;
        if (account.getRole() == Role.CUSTOMER)
            dto = orderService.findByIdAndAccount(account.getId(), orderId);
        else
            dto = orderService.findByIdAndAccount(null, orderId);

        if(dto == null){
            throw new DescriptionException("Order is not found or you don't have permission to access this order");
        }
        return dto;
    }

    public OrderDtoRequest save(OrderDtoRequest orderDtoRequest) throws ArchitectureException, MessagingException {
        validateOrderDto(orderDtoRequest);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<Payment> paymentFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return getPayment(orderDtoRequest.getOrderDto().getPaymentId());
            } catch (ForeignKeyIsNotFound e) {
                throw new RuntimeException(e);
            }
        }, executorService);

        CompletableFuture<DeliveryAddress> deliveryAddressFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return getDeliveryAddress(orderDtoRequest.getOrderDto().getDeliveryAddressId());
            } catch (ForeignKeyIsNotFound e) {
                throw new RuntimeException(e);
            }
        }, executorService);

        CompletableFuture<Voucher> voucherFuture = CompletableFuture.supplyAsync(() ->
                getVoucher(orderDtoRequest.getOrderDto().getVoucherId()), executorService);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(paymentFuture, deliveryAddressFuture, voucherFuture);

        allOf.join(); // Chờ tất cả các tác vụ hoàn thành

        Account account = getAccount();
        Payment payment = paymentFuture.join();
        DeliveryAddress deliveryAddress = deliveryAddressFuture.join();
        Voucher voucher = voucherFuture.join();

        CompletableFuture<OrderDtoRequest> completableFuture = CompletableFuture.supplyAsync(() ->
        {
            try {
                return orderProcessor.saveAsync(orderDtoRequest, payment, account, voucher, deliveryAddress).join();
            } catch (ArchitectureException e) {
                throw new RuntimeException(e);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }, executorService);

        return completableFuture.join();
    }

    private void validateOrderDto(OrderDtoRequest orderDtoRequest) throws IdMustBeNullException {
        if (orderDtoRequest.getOrderDto().getId() != null) {
            throw new IdMustBeNullException(Order.class.getSimpleName());
        }
    }

    private Payment getPayment(String paymentId) throws ForeignKeyIsNotFound {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ForeignKeyIsNotFound("PaymentId"));
    }

    private DeliveryAddress getDeliveryAddress(String deliveryAddressId) throws ForeignKeyIsNotFound {
        return deliveryAddressRepository.findById(deliveryAddressId)
                .orElseThrow(() -> new ForeignKeyIsNotFound("Delivery Address"));
    }

    private Voucher getVoucher(String voucherId) {
        return (voucherId != null) ? voucherRepository.findById(voucherId).orElse(null) : null;
    }

// Assuming getAccount() and other necessary methods are implemented elsewhere


    public OrderDto updateTypeOrder(String id, TypeOrder typeOrder) throws ArchitectureException, MessagingException {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) throw new InvalidParamException();
        order.get().setTypeOrder(typeOrder);
        return orderService.saveOrder(order.get());
    }

    public void redirectToFrontEnd(HttpServletRequest request,
                                     HttpServletResponse response
    ) throws IOException, ArchitectureException, MessagingException {
        int index = vnPayService.orderReturn(request);
        if(index == 0){
            response.sendRedirect(URL_RETURN_FRONTEND + "?index=0");
        }else{
            response.sendRedirect(URL_RETURN_FRONTEND + "?index=1");
            updateTypeOrder(request.getParameter("vnp_TxnRef"), TypeOrder.PROCESSING);
        }
    }

        public String payment(String orderId,
                            HttpServletRequest request) throws IOException, ArchitectureException {

            OrderDto orderDto = orderService.findOrderById(orderId);
            Account account = getAccount();
            if(orderDto == null) throw new EntityNotFoundException();
            System.out.println("Data từ đơn hàng: "+orderDto.getAccountId());
            System.out.println("Data từ người dùng thanh toán: "+orderDto.getAccountId());
            System.out.println("Trạng thái đơn hàng: "+orderDto.getTypeOrder());
            // nếu order đã thanh toán hoặc không phải của account đang đăng nhập
            if(orderDto.getTypeOrder() != TypeOrder.WAIT_TO_PAY && orderDto.getTypeOrder() != TypeOrder.PENDING||
                    !orderDto.getAccountId().equals(account.getId()))
                throw new DescriptionException("This order maybe has been paid or isn't your order");
            int total = orderDto.getTotal().intValue() * 24000;
            return vnPayService.create(total,
                    orderDto.getId(),
                    request);
        }
}