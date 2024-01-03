package apishop.facade;

import apishop.entity.Account;
import apishop.entity.Voucher;
import apishop.entity.VoucherOfAccount;
import apishop.exception.common.CanNotAddException;
import apishop.exception.common.ForeignKeyIsNotFound;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.SearchCriteria;
import apishop.model.dto.VoucherOfAccountDto;
import apishop.repository.VoucherRepository;
import apishop.service.VoucherOfAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static apishop.util.method.Authentication.getAccount;

@Service
@RequiredArgsConstructor
public class VoucherOfAccountFacade {

    private final VoucherOfAccountService voucherOfAccountService;
    private final VoucherRepository voucherRepository;

    //Mình sửa lại thành list là vì lỡ một id có nhiều voucherOfAccount quá thì nó lại hợp lý
    //Tìm kiếm voucherOfAccount theo id của account
    public Page<VoucherOfAccountDto> findAllByAccountId(String accountId, SearchCriteria searchCriteria) throws ArchitectureException {
        if (accountId == null)
            throw new InvalidParamException();
        Page<VoucherOfAccountDto> list = voucherOfAccountService.findAllByAccountId(searchCriteria, accountId);
        if (list.isEmpty())
            throw new EntityNotFoundException();
        return list;
    }

    public VoucherOfAccountDto create(VoucherOfAccountDto voucherOfAccountDto) throws ArchitectureException {
        if (voucherOfAccountDto.getId() != null)
            throw new IdMustBeNullException(VoucherOfAccount.class.getSimpleName());

        Account account = getAccount();
        Optional<Voucher> voucher = voucherRepository.findById(voucherOfAccountDto.getVoucherId());

        if (voucher.isEmpty()) throw new ForeignKeyIsNotFound("Voucher");
        // Kiểm tra xem voucher có hợp lệ không
        Date thisDate = new Date();

        VoucherOfAccountDto dto = voucherOfAccountService.findByAccountIdAndVoucherId(account.getId(), voucher.get().getId());
        if (voucher.get().getExpirationDate().before(thisDate)
                || voucher.get().getQuantity() == 0
                || dto != null)
            throw new CanNotAddException(VoucherOfAccount.class.getSimpleName());

        return voucherOfAccountService.save(account, voucher.get());
    }

    public void delete(String voucherId, String accountId) throws ArchitectureException {
        VoucherOfAccountDto dto = voucherOfAccountService.findByAccountIdAndVoucherId(accountId, voucherId);
        if (dto == null) throw new EntityNotFoundException();
        voucherOfAccountService.delete(dto.getId());
    }
}
