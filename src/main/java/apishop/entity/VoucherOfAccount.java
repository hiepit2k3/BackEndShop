package  apishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("voucherOfAccounts")
@Builder
public class VoucherOfAccount {

    @Id
    private String id;
    @DBRef
    private Account account;
    @DBRef
    private Voucher voucher;
    private boolean isUsed;
    public VoucherOfAccount(Account account, Voucher voucher) {
        this.account = account;
        this.voucher = voucher;
    }
}
