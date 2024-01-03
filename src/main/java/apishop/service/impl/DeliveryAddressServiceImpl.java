package apishop.service.impl;

import apishop.entity.Account;
import apishop.entity.DeliveryAddress;
import apishop.model.dto.DeliveryAddressDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.DeliveryAddressMapper;
import apishop.repository.DeliveryAddressRepository;
import apishop.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final DeliveryAddressMapper deliveryAddressMapper;

    @Override
    public DeliveryAddressDto save(DeliveryAddressDto deliveryAddress, Account account) {
        return deliveryAddressMapper.apply(
                deliveryAddressRepository.save(
                        deliveryAddressMapper.applyDeliveryAddress(deliveryAddress, account)));
    }

    @Override
    public DeliveryAddressDto findById(String id) {
        Optional<DeliveryAddress> deliveryAddress = deliveryAddressRepository.findById(id);
        return deliveryAddress.map(deliveryAddressMapper::apply).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        deliveryAddressRepository.deleteById(id);
    }


    @Override
    public Page<DeliveryAddressDto> findAll(SearchCriteria searchCriteria) {
        Page<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAll(getPageable(searchCriteria));
        return deliveryAddresses.map(deliveryAddressMapper::apply);
    }

    @Override
    public Page<DeliveryAddressDto> findAllByAccountId(SearchCriteria searchCriteria, String accountId) {
        Page<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByAccountId(accountId, getPageable(searchCriteria));
        return deliveryAddresses.map(deliveryAddressMapper::apply);
    }
}
