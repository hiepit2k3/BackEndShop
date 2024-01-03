package apishop.service;

import apishop.entity.Account;
import apishop.entity.Voucher;
import apishop.model.dto.SearchCriteria;
import apishop.model.dto.VoucherOfAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface VoucherOfAccountService {
    Page<VoucherOfAccountDto> findAllByAccountId(SearchCriteria searchCriteria, String accountId);

    VoucherOfAccountDto save(Account account, Voucher voucher);

    boolean updateIsUsed(String accountId, String voucherId);

    void delete(String voucherOfAccountId);

    VoucherOfAccountDto findByAccountIdAndVoucherId(String accountId, String voucherId);
}
