package apishop.facade;

import apishop.entity.Account;
import apishop.exception.common.DescriptionException;
import apishop.exception.common.IdMustBeNullException;
import apishop.exception.common.InvalidParamException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.AccountDto;
import apishop.model.dto.ChangePasswordDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.ChangeProfileRequest;
import apishop.repository.AccountRepository;
import apishop.service.AccountService;
import apishop.service.EncodePassword;
import apishop.util.enums.Role;
import apishop.util.enums.TypeAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static apishop.util.method.Authentication.getAccount;

@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final EncodePassword encodePassword;

    public Page<AccountDto> findByRoleAndFullName(Role role,
                                                  String fullName,
                                                  SearchCriteria searchCriteria) throws ArchitectureException {
        if (role == Role.ADMIN) throw new DescriptionException("Admin can't access");
        Page<AccountDto> page = accountService.findByRoleAndFullName(role, fullName, searchCriteria);
        return checkPageIsNotNull(page);
    }

    public AccountDto findById(String accountId) throws ArchitectureException {
        Account account = getAccount();
        if (account.getRole() == Role.ADMIN && accountId != null) {
            return checkIdIsNotNull(accountId);
        }
        return checkIdIsNotNull(account.getId());
    }

    //tạo mới account
    public AccountDto create(AccountDto accountDto) throws ArchitectureException, IOException {
        //nếu nhập thiếu dữ liệu
        if (accountDto.getId() != null)
            throw new IdMustBeNullException(Account.class.getSimpleName());
        //Kiểm tra email,username or PhoneNumber Nếu có rồi thì báo lỗi đã tồn tại
        AccountDto exist = accountService.findByUsernameOrEmailOrPhoneNumber(accountDto);
        if (exist != null) {
            throw new DescriptionException("Email, username or phone number already exists");
        }
        for (char c : accountDto.getUsername().toCharArray()) {
            if (c >= '\u00C0' && c <= '\u1EF9') {
                throw new DescriptionException("Your username is incorrect");
            }
        }
        if (accountDto.getPassword().length() < 6)
            throw new DescriptionException("Password must be at least 6 characters");
        return accountService.create(accountDto);
    }
//
//    //cập nhật account - profile - trừ mật khẩu
    public AccountDto updateProfile(ChangeProfileRequest request, String accountId) throws ArchitectureException, IOException {
        Account accountOld = getOldAccount(accountId).get();
        if(request.getPhoneNumber() != null){
            AccountDto exist = accountService.findByPhone(request.getPhoneNumber());
            if (exist != null && !exist.getId().equals(accountOld.getId()))
                throw new DescriptionException("Phone number already exists");
        }

        // Chỉ cần kiểm tra phone vì email và username không thể thay đổi
        return accountService.updateProfile(request, accountOld);
    }

    private Optional<Account> getOldAccount(String accountId) throws ArchitectureException {
        Optional<Account> accountOld;
        Account account = getAccount();
        if (account.getRole() == Role.ADMIN)
            accountOld = accountRepository.findById(accountId);
        else {
            accountOld = accountRepository.findById(account.getId());
        }
        if (accountOld.isEmpty()) throw new EntityNotFoundException();
        return accountOld;
    }

    public AccountDto changePassword(ChangePasswordDto passwordDto, String id) throws ArchitectureException {
        Optional<Account> accountOld = getOldAccount(id);
        if(accountOld.get().getRole() == Role.CUSTOMER){
            if(!encodePassword.matches(passwordDto.getOldPassword(), accountOld.get().getPassword()))
                throw new DescriptionException("Old password is incorrect");
        }
        return accountService.changePassword(accountOld.get(), passwordDto);
    }

    public AccountDto findByUsernameOrEmail(String username, String email) throws ArchitectureException {
        if (username.isEmpty() || email.isEmpty())
            throw new InvalidParamException();
        return accountService.findByUsernameOrEmail(username, email);
    }

    public Page<AccountDto> filter(SearchCriteria searchCriteria,
                                   Role role) throws ArchitectureException {
        if(role == Role.ADMIN) throw new DescriptionException("Admin can't access");

        Page<AccountDto> page = accountService
                .filterRoleAndSexOrStatus(searchCriteria, role);
        return checkPageIsNotNull(page);
    }

    public AccountDto findByRoleAndUsernameOrEmail(Role role, String usernameOrEmail) throws ArchitectureException {
        if(role == Role.ADMIN) throw new DescriptionException("Admin can't access");
        if (usernameOrEmail.isEmpty())
            throw new InvalidParamException();
        AccountDto accountDto = accountService.findByRoleAndUsernameOrEmail(role, usernameOrEmail, usernameOrEmail);
        return checkAccountIsNotNull(accountDto);
    }


    public AccountDto lockAndUnlock(TypeAccount typeAccount, String id) throws EntityNotFoundException {
        Account account = getAccountEntity(id);
        return accountService.lockAndUnlockAccount(account, typeAccount);

    }
    private AccountDto checkAccountIsNotNull(AccountDto accountDto) throws EntityNotFoundException {
        if (accountDto == null)
            throw new EntityNotFoundException();
        return accountDto;
    }

    private AccountDto checkIdIsNotNull(String id) throws EntityNotFoundException {
        AccountDto accountDto = accountService.findById(id);
        if (accountDto == null) throw new EntityNotFoundException();
        return accountDto;
    }

    // Sử dụng cho các phương thức update đơn dữ liệu vì accountDto không thể lấy password
    private Account getAccountEntity(String id) throws EntityNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) throw new EntityNotFoundException();
        return account.get();
    }

    private Page<AccountDto> checkPageIsNotNull(Page<AccountDto> accountsDto) throws EntityNotFoundException {
        if (accountsDto.isEmpty()) throw new EntityNotFoundException();
        return accountsDto;
    }
}
