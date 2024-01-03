package apishop.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class VoucherOfAccountDto {
    private String id;
    private String accountId;
    @NonNull
    private String voucherId;

}
