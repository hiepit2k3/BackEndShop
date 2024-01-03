package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.facade.AccountFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.dto.AccountDto;
import apishop.model.dto.ChangePasswordDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.ChangeProfileRequest;
import apishop.service.AccountService;
import apishop.util.enums.Role;
import apishop.util.enums.TypeAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static apishop.util.api.ConstantsApi.Account.ACCOUNT_PATH;

@RestController
@RequestMapping(ACCOUNT_PATH)
@RequiredArgsConstructor
public class AccountController {
    private final AccountFacade accountFacade;
    @PostMapping("/create")
    public ResponseEntity<Object> create(AccountDto accountDto) throws IOException, ArchitectureException {
        System.out.println(accountDto);
        accountFacade.create(accountDto);
        return ResponseHandler.response(HttpStatus.OK, "Tạo mới thành công",true);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<Object> updateAccount(
            @RequestParam(required = false) String accountId,
            ChangeProfileRequest request) throws ArchitectureException, IOException {
        return ResponseHandler.response(HttpStatus.OK,
                accountFacade.updateProfile(request, accountId), true);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Object> changePassword(
            @RequestParam(required = false) String accountId,
            @RequestBody ChangePasswordDto model) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                accountFacade.changePassword(model, accountId), true);
    }

    @GetMapping("/id")
    public ResponseEntity<Object> getAccountById(
            @RequestParam(required = false) String accountId) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, accountFacade.findById(accountId), true);
    }

    @GetMapping("/{role}")
    public ResponseEntity<Object> getAllAccountByRole(
            @PathVariable Role role,
            @RequestParam(required = false) TypeAccount typeAccount,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException {
        Page<AccountDto> list = accountFacade
                .filter(new SearchCriteria(page, size, columSort), role);
        return ResponseHandler.response(HttpStatus.OK, list, true);
    }

    @GetMapping("/{role}/usernameOrEmail")
    public ResponseEntity<Object> getAllAccountByUsernameOrEmail(
            @RequestParam String usernameOrEmail,
            @PathVariable Role role
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                accountFacade.findByRoleAndUsernameOrEmail(role, usernameOrEmail), true);
    }


    @GetMapping("/{role}/fullName")
    public ResponseEntity<Object> getAllAccountByFullName(
            @RequestParam String fullName,
            @PathVariable Role role,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException {
        Page<AccountDto> pageAccounts =
                accountFacade.findByRoleAndFullName(role, fullName, new SearchCriteria(page, size, columSort));
        return ResponseHandler.response(HttpStatus.OK, pageAccounts, true);
    }

    @PutMapping("/lock/{typeAccount}/{id}")
    public ResponseEntity<Object> lockAndUnLock(@PathVariable TypeAccount typeAccount, @PathVariable String id) throws EntityNotFoundException, EntityNotFoundException {
        return ResponseHandler.response(HttpStatus.OK, accountFacade.lockAndUnlock(typeAccount, id),true);
    }

}
