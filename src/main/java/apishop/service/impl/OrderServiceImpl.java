package apishop.service.impl;


import apishop.entity.*;
import apishop.exception.common.DescriptionException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.facade.OrderDetailFacade;
import apishop.facade.ProductVariantFacade;
import apishop.model.dto.*;
import apishop.model.mapper.*;
import apishop.repository.OrderDetailRepository;
import apishop.repository.OrderRepository;
import apishop.service.*;
import apishop.model.request.OrderDtoRequest;
import apishop.util.enums.TypeDiscount;
import apishop.util.enums.TypeOrder;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    //region
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final VoucherOfAccountService voucherOfAccountService;
    private final ProductVariantFacade productVariantFacade;
    private final OrderDetailFacade orderDetailFacade;
    private final MailService mailService;
    private final OrderDetailMapper orderDetailMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final PaymentMapper paymentMapper;
    private final AccountMapper accountMapper;
    private final VoucherMapper voucherMapper;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public Page<OrderDtoRequest> findAllByAccountIdAndTypeOrder(String accountId,
                                                                TypeOrder typeOrder,
                                                                SearchCriteria searchCriteria) {
        Page<Order> orders = orderRepository
                .findAllByAccountIdAndTypeOrder(accountId, typeOrder, getPageable(searchCriteria)); // Lấy danh sách các đối tượng Order
        List<OrderDtoRequest> orderDtoRequests = orders.stream()
                .map(order -> {
                    // Lấy ra danh sách orderDetailDto
                    List<OrderDetail> orderDetailDto = orderDetailRepository.findAllByOrderId(order.getId());
                    List<OrderDetailDto> orderDetailDtoList = orderDetailMapper.applyAll(orderDetailDto);

                    // Lấy ra accountDto
                    AccountDto accountDto = accountMapper.apply(order.getAccount());

                    OrderDto orderDto = orderMapper.apply(order); // Đảm bảo có phương thức ánh xạ từ Order sang OrderDto

                    return new OrderDtoRequest(orderDto, accountDto, orderDetailDtoList);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(orderDtoRequests,
                getPageable(searchCriteria),
                orders.getTotalElements());

    }

    @Override
    public Page<OrderDtoRequest> findAllByTypeOrder(
            TypeOrder typeOrder,
            SearchCriteria searchCriteria) {
        Page<Order> orders;
        if (typeOrder == null) {
            System.out.println("null");
            orders = orderRepository.findAll(getPageable(searchCriteria));
        } else {
            orders = orderRepository
                    .findAllByTypeOrder(typeOrder, getPageable(searchCriteria));
        }
        List<OrderDtoRequest> orderDtoRequests = orders.stream()
                .map(order -> {
                    // Lấy ra danh sách orderDetailDto
                    System.out.println(order);
                    List<OrderDetail> orderDetailDto = orderDetailRepository.findAllByOrderId(order.getId());
                    List<OrderDetailDto> orderDetailDtoList = orderDetailMapper.applyAll(orderDetailDto);

                    // Lấy ra accountDto
                    AccountDto accountDto = accountMapper.apply(order.getAccount());

                    OrderDto orderDto = orderMapper.apply(order); // Đảm bảo có phương thức ánh xạ từ Order sang OrderDto

                    return new OrderDtoRequest(orderDto, accountDto, orderDetailDtoList);
                })
                .collect(Collectors.toList());
        System.out.println(orders.getTotalElements());
        return new PageImpl<>(orderDtoRequests,
                getPageable(searchCriteria),
                orders.getTotalElements());

    }


    @Transactional(rollbackFor = ArchitectureException.class)
    @Override
    public OrderDtoRequest save(OrderDtoRequest orderDtoRequest,
                                Payment payment,
                                Account account,
                                Voucher voucher,
                                DeliveryAddress deliveryAddress
    ) throws ArchitectureException, MessagingException {
        double total = orderDtoRequest.getOrderDto().getTotal();
        double discount = 0;

        // Xóa voucher được áp dụng
        if (voucher != null) {
            if (!voucherOfAccountService.updateIsUsed(account.getId(), voucher.getId())
                    || voucher.getRegisterDate().after(new Date()))
                throw new DescriptionException("Voucher is used or cannot be used yet");
            if (total > voucher.getMinTotal()) {
                if (voucher.getTypeDiscount() == TypeDiscount.PERCENT) {
                    discount = total * voucher.getDiscount();
                    if (discount > voucher.getMaxDiscount()) {
                        discount = voucher.getMaxDiscount();
                    }
                } else {
                    discount = voucher.getDiscount();
                }
            }
        }
        Order order = orderRepository.save(
                orderMapper.applyToOrder(
                        orderDtoRequest.getOrderDto(),
                        payment,
                        account,
                        voucher,
                        deliveryAddress));
        //
        orderDtoRequest.setOrderDto(orderMapper.apply(order));

        List<OrderDetailDto> orderDetailDtoList =
                saveOrderDetailsAndQuantity(order, orderDtoRequest.getOrderDetailsDto());

        orderDtoRequest.setOrderDetailsDto(orderDetailDtoList);

        mailService.sendOrderEmail(order.getAccount().getEmail(),
                order, orderDetailDtoList, discount);
        return orderDtoRequest;

    }

    @Override
    public OrderIdentity findByIdAndAccount(String accountId, String orderId) throws ArchitectureException {

        Order order = orderRepository.findAllByAccountIdAndId(accountId, orderId);
        if (order == null) {
            throw new EntityNotFoundException();
        }
        OrderIdentity orderIdentity = new OrderIdentity();
        orderIdentity.setOrderDto(orderMapper.apply(order));

        DeliveryAddressDto deliveryAddressDto = deliveryAddressMapper.apply(order.getDeliveryAddress());
        orderIdentity.setDeliveryAddressDto(deliveryAddressDto);

        PaymentDto paymentDto = paymentMapper.apply(order.getPayment());
        orderIdentity.setPaymentDto(paymentDto);

        AccountDto accountDto = accountMapper.apply(order.getAccount());
        orderIdentity.setAccountDto(accountDto);

        if (order.getVoucher() != null) {
            VoucherDto voucherDto = voucherMapper.apply(order.getVoucher());
            orderIdentity.setVoucherDto(voucherDto);
        }

        List<OrderDetailDto> orderDetailDtoList = orderDetailMapper.applyAll(orderDetailRepository.findAllByOrderId(orderId));
        orderIdentity.setOrderDetailsDto(orderDetailDtoList);

        return orderIdentity;
    }

    @Override
    public OrderDto findOrderById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(orderMapper).orElse(null);
    }

    @Override
    public List<OrderDto> findAllByDeliveryAddressId(String deliveryAddressId) {
        List<Order> orders = orderRepository.findAllByDeliveryAddressId(deliveryAddressId);
        return orders.stream().map(orderMapper).collect(Collectors.toList());
    }

    @Override
    public OrderDto saveOrder(Order order) throws MessagingException {
        Order orderNew = orderRepository.save(order);
        mailService.sendUpdateOrderEmail(
                orderNew.getAccount().getEmail(),
                orderNew.getId(),
                orderNew.getTypeOrder());
        return orderMapper.apply(orderNew);
    }

    public List<OrderDetailDto> saveOrderDetailsAndQuantity(Order order,
                                                            List<OrderDetailDto> orderDetailDtoList
    ) throws ArchitectureException {

        List<ProductVariant> productVariants = new ArrayList<>();
        // Lọc qua để set lại số lượng và orderCount của product - variant
        for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
            // lấy từ database và đã kiểm tra ở Facade
            ProductVariant productVariant = productVariantFacade.getEntity(orderDetailDto.getProductVariantId());
            Integer quantity = orderDetailDto.getQuantity();

            productVariant.setQuantity(productVariant.getQuantity() - quantity);
            productVariants.add(productVariant);
            Product product = productVariant.getProduct();
            product.setOrderCount(product.getOrderCount() == null ? quantity : product.getOrderCount() + quantity);

        }

        return orderDetailFacade.saveAll(orderDetailDtoList, productVariants, order);
    }
}

