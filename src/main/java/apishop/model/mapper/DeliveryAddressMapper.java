package apishop.model.mapper;

import apishop.entity.Account;
import apishop.entity.DeliveryAddress;
import apishop.model.dto.DeliveryAddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DeliveryAddressMapper implements Function<DeliveryAddress, DeliveryAddressDto> {


    @Override
    public DeliveryAddressDto apply(DeliveryAddress deliveryAddress) {
        return DeliveryAddressDto
                .builder()
                .id(deliveryAddress.getId())
                .phoneNumber(deliveryAddress.getPhoneNumber())
                .city(deliveryAddress.getCity())
                .cityCode(deliveryAddress.getCityCode())
                .apartmentNumber(deliveryAddress.getApartmentNumber())
                .ward(deliveryAddress.getWard())
                .wardCode(deliveryAddress.getWardCode())
                .district(deliveryAddress.getDistrict())
                .districtCode(deliveryAddress.getDistrictCode())
                .build()
                ;
    }

    public DeliveryAddress applyDeliveryAddress(DeliveryAddressDto deliveryAddress, Account account) {
        return DeliveryAddress
                .builder()
                .id(deliveryAddress.getId())
                .phoneNumber(deliveryAddress.getPhoneNumber())
                .city(deliveryAddress.getCity())
                .cityCode(deliveryAddress.getCityCode())
                .apartmentNumber(deliveryAddress.getApartmentNumber())
                .ward(deliveryAddress.getWard())
                .wardCode(deliveryAddress.getWardCode())
                .district(deliveryAddress.getDistrict())
                .districtCode(deliveryAddress.getDistrictCode())
                .account(account)
                .build();
    }
}
