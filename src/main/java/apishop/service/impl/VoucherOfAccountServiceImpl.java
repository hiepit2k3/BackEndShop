package apishop.service.impl;

import apishop.entity.Account;
import apishop.entity.Voucher;
import apishop.entity.VoucherOfAccount;
import apishop.model.dto.SearchCriteria;
import apishop.model.dto.VoucherOfAccountDto;
import apishop.model.mapper.VoucherOfAccountMapper;
import apishop.repository.VoucherOfAccountRepository;
import apishop.service.VoucherOfAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class VoucherOfAccountServiceImpl implements VoucherOfAccountService {

    private final VoucherOfAccountRepository voucherOfAccountRepository;
    private final VoucherOfAccountMapper voucherOfAccountMapper;

    /**
     * find voucherOfAccount with accountId
     *
     * @param accountId
     * @return voucherOfAccount
     */
    @Override
    public Page<VoucherOfAccountDto> findAllByAccountId(SearchCriteria searchCriteria, String accountId) {
        Page<VoucherOfAccount> voucherOfAccount = voucherOfAccountRepository.findAllByAccountId(accountId, getPageable(searchCriteria));
        return voucherOfAccount.map(voucherOfAccountMapper::apply);
    }

    @Override
    public VoucherOfAccountDto save(Account account, Voucher voucher){
        voucher.setQuantity(voucher.getQuantity() - 1);
        return voucherOfAccountMapper.apply(
                voucherOfAccountRepository.save(new VoucherOfAccount(account,voucher)));
    }

    @Override
    public VoucherOfAccountDto findByAccountIdAndVoucherId(String accountId, String voucherId) {
        Optional<VoucherOfAccount> voucherOfAccount =
                voucherOfAccountRepository.findByAccountIdAndVoucherId(accountId,voucherId);
        return voucherOfAccount.map(voucherOfAccountMapper::apply).orElse(null);
    }

    @Override
    public boolean updateIsUsed(String accountId, String voucherId) {
        Optional<VoucherOfAccount> voucherOfAccount =
                voucherOfAccountRepository.findByAccountIdAndVoucherId(accountId,voucherId);

        if (voucherOfAccount.get().isUsed()) return false;

        voucherOfAccount.get().setUsed(true);
        voucherOfAccountRepository.save(voucherOfAccount.get());
        return true;
    }

    @Override
    public void delete(String voucherOfAccountId) {
        voucherOfAccountRepository.deleteById(voucherOfAccountId);
    }
}
