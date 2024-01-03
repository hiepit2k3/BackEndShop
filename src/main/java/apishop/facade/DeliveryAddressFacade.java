package apishop.facade;

import apishop.entity.Account;
import apishop.entity.DeliveryAddress;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.DescriptionException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.DeliveryAddressDto;
import apishop.model.dto.OrderDto;
import apishop.model.dto.SearchCriteria;
import apishop.service.DeliveryAddressService;
import apishop.service.OrderService;
import apishop.util.enums.TypeOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static apishop.util.method.Authentication.getAccount;

@Service
@RequiredArgsConstructor
public class DeliveryAddressFacade {
    private final DeliveryAddressService deliveryAddressService;
    private final OrderService orderService;

    //Tạo mới một địa chỉ giao hàng
    public DeliveryAddressDto create(DeliveryAddressDto deliveryAddress) throws ArchitectureException {
        if (deliveryAddress.getId() != null)
            throw new IdMustBeNullException(DeliveryAddress.class.getSimpleName());
        Account account = getAccount();
        return deliveryAddressService.save(deliveryAddress, account);
    }

    public DeliveryAddressDto update(DeliveryAddressDto deliveryAddress, String id) throws ArchitectureException {
        checkNotNull(id);
        deliveryAddress.setId(id);
        Account account = getAccount();
        List<OrderDto> ordersDto = orderService.findAllByDeliveryAddressId(deliveryAddress.getId());
        if (!ordersDto.isEmpty()) {
            for (OrderDto orderDto : ordersDto) {
                if (orderDto.getTypeOrder() != TypeOrder.RETURNED &&
                        orderDto.getTypeOrder() != TypeOrder.CANCELLED &&
                        orderDto.getTypeOrder() != TypeOrder.SUCCESSFUL) {
                    throw new DescriptionException("This address is being used by an order, so it cannot be changed");
                }
            }
        }

        return deliveryAddressService.save(deliveryAddress, account);
    }

    //Tìm kiếm địa chỉ giao hàng theo id
    public DeliveryAddressDto findById(String id) throws ArchitectureException {
        return checkNotNull(id);
    }

    private DeliveryAddressDto checkNotNull(String id) throws EntityNotFoundException {
        DeliveryAddressDto dto = deliveryAddressService.findById(id);
        if (dto == null) {
            throw new EntityNotFoundException();
        }
        return dto;
    }

    //Xóa địa chỉ giao hàng theo id
    public void deleteById(String id) throws ArchitectureException {
        checkNotNull(id);
        try {
            deliveryAddressService.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new CanNotDeleteException("delivery address");
        }
    }

    //Tìm kiếm tất cả địa chỉ giao hàng
    public Page<DeliveryAddressDto> findAll(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<DeliveryAddressDto> list = deliveryAddressService.findAll(searchCriteria);
        if (list.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return list;
    }

    //Tìm kiếm tất cả địa chỉ giao hàng theo id account
    public Page<DeliveryAddressDto> findAllByAccountId(SearchCriteria searchCriteria
    ) throws ArchitectureException {
        Account account = getAccount();
        Page<DeliveryAddressDto> list = deliveryAddressService.findAllByAccountId(searchCriteria, account.getId());
        if (list.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return list;
    }
}