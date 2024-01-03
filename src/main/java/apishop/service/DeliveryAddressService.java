package apishop.service;


import apishop.entity.Account;
import apishop.model.dto.DeliveryAddressDto;
import apishop.model.dto.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAddressService {
    DeliveryAddressDto save(DeliveryAddressDto deliveryAddressDto, Account account);

    DeliveryAddressDto findById(String id);

    void deleteById(String id);

    Page<DeliveryAddressDto> findAll(SearchCriteria searchCriteria);

    Page<DeliveryAddressDto> findAllByAccountId(SearchCriteria searchCriteria, String accountId);
}
