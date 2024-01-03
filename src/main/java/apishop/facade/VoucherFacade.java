package apishop.facade;
import apishop.entity.Account;
import apishop.entity.Voucher;
import apishop.exception.common.CanNotDeleteException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.SearchCriteria;
import apishop.model.dto.VoucherDto;
import apishop.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static apishop.util.method.Authentication.getAccount;

@Service
@RequiredArgsConstructor
public class VoucherFacade {

    private final VoucherService voucherService;
//    private final S3AmazonService s3AmazonService;

    public Page<VoucherDto> findAll(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<VoucherDto> listVouchers = voucherService.findAllVoucher(searchCriteria);
        if (listVouchers.isEmpty()) {
            throw new EntityNotFoundException();
//               trường hợp không tồn tại sẽ bắn lỗi
        }
        return listVouchers;
    }

    public VoucherDto findVoucherById(String voucherId) throws ArchitectureException {
        return checkNotNull(voucherId);
    }

    public VoucherDto save(VoucherDto voucherDto) throws ArchitectureException, IOException {
        if (voucherDto.getId() != null) throw new IdMustBeNullException(Voucher.class.getSimpleName());
        return voucherService.save(voucherDto);
    }

    public VoucherDto update(String voucherId, VoucherDto voucherDto) throws ArchitectureException, IOException {
        VoucherDto voucherDtoOld = checkNotNull(voucherId);
        voucherDto.setId(voucherId);
        voucherDto.setImage(voucherDtoOld.getImage());
        return voucherService.save(voucherDto);
    }

    public void deleteVoucherById(String voucherId) throws ArchitectureException {
        checkNotNull(voucherId);
        try {
            voucherService.delete(voucherId);
        } catch (DataIntegrityViolationException e) {
            throw new CanNotDeleteException("voucher");
        }
    }

    private VoucherDto checkNotNull(String voucherId) throws EntityNotFoundException {
        VoucherDto existVoucherDto = voucherService.findById(voucherId);
        if (existVoucherDto == null) {
            throw new EntityNotFoundException();
        }
        return existVoucherDto;
    }

    public List<VoucherDto> findAllByAccountId() throws ArchitectureException {
        Account account = getAccount();
        List<VoucherDto> voucherDtoList = voucherService.findAllByAccount(account.getId());
        if (voucherDtoList.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return voucherDtoList;
    }

    public Page<VoucherDto> getVoucherDtoByExpirationDate(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<VoucherDto> voucherDto;
        try {
            Account account = getAccount();
             voucherDto = voucherService.findByExpirationDate(searchCriteria, account.getId());
        } catch (ClassCastException e){
            voucherDto = voucherService.findByExpirationDate(searchCriteria, null);
        }
        if (voucherDto == null) {
            throw new EntityNotFoundException();
        }
        return voucherDto;
    }

}
