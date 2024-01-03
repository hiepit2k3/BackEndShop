package  apishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("deliveryAddresses")
@Builder
public class DeliveryAddress {
    @Id
    private String id;
    private String city;
    private Integer cityCode;
    private String district;
    private Integer districtCode;
    private String ward;
    private Integer wardCode;
    private String apartmentNumber;
    private String phoneNumber;
    @DBRef
    private Account account;
    @DBRef
    private Set<Order> orders;

    public DeliveryAddress(String id, String city, String district, String ward, String apartmentNumber, String phoneNumber) {
        this.id = id;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
    }
}
