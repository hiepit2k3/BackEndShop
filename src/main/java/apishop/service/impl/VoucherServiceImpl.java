package apishop.service.impl;

import apishop.entity.Voucher;
import apishop.model.dto.SearchCriteria;
import apishop.model.dto.VoucherDto;
import apishop.model.mapper.VoucherMapper;
import apishop.repository.VoucherRepository;
import apishop.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static apishop.util.method.Search.getPageable;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    /**
     * find voucher by id
     *
     * @param voucherId
     * @return voucher
     */
    @Override
    public VoucherDto findById(String voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        return voucher.map(voucherMapper :: apply).orElse(null);
    }

    /**
     * save voucher
     *
     * @param voucherDto
     * @return voucher
     */
    @Override
    public VoucherDto save(VoucherDto voucherDto) throws IOException {
        Voucher voucher = voucherMapper.applyVoucher(voucherDto);
        return voucherMapper.apply(voucherRepository.save(voucher));
    }

    // phương thức sửa voucher thì dùng findId xong rồi save lại nhóe

    /**
     * Delete voucher
     *
     * @param voucherId
     */
    @Override
    public void delete(String voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    /**
     * find all voucher use pageable
     *
     * @return all user
     */
    @Override
    public Page<VoucherDto> findAllVoucher(SearchCriteria searchCriteria) {
        Page<Voucher> vouchers = voucherRepository.findAll(getPageable(searchCriteria));
        return vouchers.map(voucherMapper::apply);
    }

    @Override
    public Page<VoucherDto> findByExpirationDate(SearchCriteria searchCriteria, String accountId){
        Page<Voucher> vouchers = voucherRepository.findAllIsActived(getPageable(searchCriteria), accountId);
        return vouchers.map(voucherMapper::apply);
    }

    @Override
    public List<VoucherDto> findAllByAccount(String accountId){
        List<Voucher> voucherList = voucherRepository.findAllByVoucherOfAccountsAccountId(accountId);
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        for(Voucher v : voucherList){
            voucherDtoList.add(voucherMapper.apply(v));
        }
        return voucherDtoList;
    }
}
