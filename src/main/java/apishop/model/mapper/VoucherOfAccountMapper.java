package apishop.model.mapper;
import apishop.entity.VoucherOfAccount;
import apishop.model.dto.VoucherOfAccountDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VoucherOfAccountMapper implements Function<VoucherOfAccount, VoucherOfAccountDto> {


    @Override
    public VoucherOfAccountDto apply(VoucherOfAccount voucherOfAccount) {
        return VoucherOfAccountDto.builder()
                .id(voucherOfAccount.getId())
                .accountId(voucherOfAccount.getAccount().getId())
                .voucherId(voucherOfAccount.getVoucher().getId())
                .build();
    }

}
