package apishop.service;

import apishop.model.dto.SearchCriteria;
import apishop.model.dto.VoucherDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public interface VoucherService {
    VoucherDto findById(String voucherId);
    VoucherDto save(VoucherDto voucher) throws IOException;
    void delete(String voucherId);
    Page<VoucherDto> findAllVoucher(SearchCriteria searchCriteria);

    Page<VoucherDto> findByExpirationDate(SearchCriteria searchCriteria, String accountId);

    List<VoucherDto> findAllByAccount(String accountId);
}
