package  apishop.entity;

import apishop.util.enums.TypeOrder;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("orders")
@Builder
public class Order {

    @Id
    private String id;
    private Date purchaseDate;
    private Double total;
    private TypeOrder typeOrder;
    @DBRef
    private Payment payment;
    @DBRef
    private Account account;
    @DBRef
    private DeliveryAddress deliveryAddress;
    @DBRef
    private Voucher voucher;
    @DBRef
    private Set<OrderDetail> orderDetails;
}
