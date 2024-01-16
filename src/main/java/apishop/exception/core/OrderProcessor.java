package apishop.exception.core;

import apishop.entity.*;
import apishop.exception.common.DescriptionException;
import apishop.facade.OrderDetailFacade;
import apishop.facade.ProductVariantFacade;
import apishop.model.dto.OrderDetailDto;
import apishop.model.mapper.*;
import apishop.model.request.OrderDtoRequest;
import apishop.repository.OrderDetailRepository;
import apishop.repository.OrderRepository;
import apishop.service.MailService;
import apishop.service.VoucherOfAccountService;
import apishop.util.enums.TypeDiscount;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderProcessor {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final VoucherOfAccountService voucherOfAccountService;
    private final ProductVariantFacade productVariantFacade;
    private final OrderDetailFacade orderDetailFacade;
    private final MailService mailService;
    public CompletableFuture<OrderDtoRequest> saveAsync(OrderDtoRequest orderDtoRequest,
                                                        Payment payment,
                                                        Account account,
                                                        Voucher voucher,
                                                        DeliveryAddress deliveryAddress) throws ArchitectureException, MessagingException {
        return CompletableFuture.supplyAsync(() -> {
            double total = orderDtoRequest.getOrderDto().getTotal();
            double discount = 0;

            // Xử lý voucher
            try {
                if (voucher != null && isValidVoucher(voucher, account, total)) {
                    discount = calculateDiscount(total, voucher);
                }
            } catch (DescriptionException e) {
                throw new RuntimeException(e);
            }

            // Lưu order
            Order order = orderRepository.save(orderMapper.applyToOrder(orderDtoRequest.getOrderDto(), payment, account, voucher, deliveryAddress));

            // Cập nhật orderDtoRequest
            orderDtoRequest.setOrderDto(orderMapper.apply(order));
            try {
                orderDtoRequest.setOrderDetailsDto(saveOrderDetailsAndQuantity(order, orderDtoRequest.getOrderDetailsDto()));
            } catch (ArchitectureException e) {
                throw new RuntimeException(e);
            }

            // Gửi email
            try {
                mailService.sendOrderEmail(order.getAccount().getEmail(), order, orderDtoRequest.getOrderDetailsDto(), discount);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            return orderDtoRequest;
        });
    }

    private boolean isValidVoucher(Voucher voucher, Account account, double total) throws DescriptionException {
        if (!voucherOfAccountService.updateIsUsed(account.getId(), voucher.getId()) || voucher.getRegisterDate().after(new Date())) {
            throw new DescriptionException("Voucher is used or cannot be used yet");
        }
        return total > voucher.getMinTotal();
    }

    private double calculateDiscount(double total, Voucher voucher) {
        double discount = (voucher.getTypeDiscount() == TypeDiscount.PERCENT) ? total * voucher.getDiscount() : voucher.getDiscount();
        return Math.min(discount, voucher.getMaxDiscount());
    }

    public List<OrderDetailDto> saveOrderDetailsAndQuantity(Order order,
                                                            List<OrderDetailDto> orderDetailDtoList) throws ArchitectureException {
        Map<String, ProductVariant> productVariantsMap = new HashMap<>();

        for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
            ProductVariant productVariant = productVariantFacade.getEntity(orderDetailDto.getProductVariantId());
            Integer quantity = orderDetailDto.getQuantity();
            productVariant.setQuantity(productVariant.getQuantity() - quantity);
            productVariantsMap.put(orderDetailDto.getProductVariantId(), productVariant);

            Product product = productVariant.getProduct();
            product.setOrderCount((product.getOrderCount() != null ? product.getOrderCount() : 0) + quantity);
        }

        List<ProductVariant> productVariants = new ArrayList<>(productVariantsMap.values());

        return orderDetailFacade.saveAll(orderDetailDtoList, productVariants, order);
    }
}
