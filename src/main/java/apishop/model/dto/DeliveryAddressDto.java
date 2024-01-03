package apishop.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class DeliveryAddressDto {

    private String id;
    private String city;
    private Integer cityCode;
    private String district;
    private Integer districtCode;
    private String ward;
    private Integer wardCode;
    private String apartmentNumber;
    private String phoneNumber;
}
