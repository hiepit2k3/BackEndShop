package apishop.model.dto;

import apishop.util.enums.TypeOrder;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class OrderDto {

    private String id;
    private Date purchaseDate;
    private TypeOrder typeOrder;
    @NonNull
    private Double total;
    @NonNull
    private String deliveryAddressId;
    @NonNull
    private String paymentId;
    private String accountId;
    private String voucherId;
}
