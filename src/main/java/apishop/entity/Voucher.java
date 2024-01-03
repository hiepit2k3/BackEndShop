package  apishop.entity;

import apishop.util.enums.TypeDiscount;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("vouchers")
@Builder
public class Voucher {
    @Id
    private String id;
    private String name;
    private Double discount;
    private Integer quantity;
    private String description;
    private Binary image;
    private Date registerDate;
    private Date expirationDate;
    private Double minTotal;
    private Double maxDiscount;
    private TypeDiscount typeDiscount;
    @DBRef
    private Set<Order> orders;
    @DBRef
    private Set<VoucherOfAccount> voucherOfAccounts;
}
