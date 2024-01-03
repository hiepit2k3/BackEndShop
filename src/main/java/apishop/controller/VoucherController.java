package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.VoucherFacade;
import apishop.facade.VoucherOfAccountFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.SearchCriteria;
import apishop.model.dto.VoucherDto;
import apishop.model.dto.VoucherOfAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static apishop.util.api.ConstantsApi.Voucher.VOUCHER_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(VOUCHER_PATH)
public class VoucherController {

    private final VoucherFacade voucherFacade;
    private final VoucherOfAccountFacade voucherOfAccountFacade;

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "!id") String columSort
    ) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                voucherFacade.findAll(new SearchCriteria(page, size, columSort)),
                true
        );
    }

    @GetMapping("id/{voucherId}")
    public ResponseEntity<Object> findVoucherById(@PathVariable String voucherId) throws ArchitectureException {
        return ResponseHandler.response(
                HttpStatus.OK,
                voucherFacade.findVoucherById(voucherId),
                true
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createVoucher(VoucherDto voucherDto) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK, voucherFacade.save(voucherDto), true);

    }

    @PutMapping("/update/{voucherId}")
    public ResponseEntity<Object> updateVoucher(VoucherDto voucherDto, @PathVariable String voucherId) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK, voucherFacade.update(voucherId, voucherDto), true);
    }

    @DeleteMapping("/delete/{voucherId}")
    public ResponseEntity<Object> deleteVoucher(@PathVariable String voucherId) throws ArchitectureException {
        voucherFacade.deleteVoucherById(voucherId);
        return ResponseHandler.response(HttpStatus.OK, "Delete successfully", true);
    }

    @GetMapping("/account")
    public ResponseEntity<Object> findAllByAccountId() throws ArchitectureException {
        List<VoucherDto> list = voucherFacade.findAllByAccountId();
        return ResponseHandler.response(HttpStatus.OK, list, true);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addVoucherToAccount(@RequestBody VoucherOfAccountDto voucherOfAccountDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,voucherOfAccountFacade.create(voucherOfAccountDto),true);
    }

    @GetMapping("/is-active")
    public ResponseEntity<Object> findByRegisterDate(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "!id") String sort
            ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                voucherFacade.getVoucherDtoByExpirationDate(new SearchCriteria(page,size,sort)),true);
    }
}
