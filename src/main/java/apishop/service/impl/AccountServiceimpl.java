package apishop.service.impl;

import apishop.entity.Account;
import apishop.model.dto.AccountDto;
import apishop.model.dto.ChangePasswordDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.mapper.AccountMapper;
import apishop.model.request.ChangeProfileRequest;
import apishop.repository.AccountRepository;
import apishop.service.AccountService;
import apishop.service.EncodePassword;
import apishop.util.FileConverter;
import apishop.util.enums.Role;
import apishop.util.enums.TypeAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static apishop.util.method.Search.getPageable;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceimpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final EncodePassword encodePassword;


    @Override
    public AccountDto findByRoleAndUsernameOrEmail(Role Role, String username, String email) {
        Optional<Account> account = accountRepository.findByRoleAndEmailOrUsername(Role, username, email);
        return account.map(accountMapper::apply).orElse(null);
    }

    @Override
    public AccountDto findById(String id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(accountMapper::apply).orElse(null);
    }


    @Override
    public AccountDto findByPhone(String phone) {
        Optional<Account> account = accountRepository.findByPhoneNumber(phone);
        return account.map(accountMapper::apply).orElse(null);
    }

    @Override
    public Page<AccountDto> findByRoleAndFullName(Role role, String fullName, SearchCriteria searchCriteria) {
        Page<Account> pageAccount = accountRepository.findAllByRoleAndFullNameContains(role, fullName, getPageable(searchCriteria));
        return pageAccount.map(accountMapper::apply);
    }


    @Override
    public AccountDto findByUsernameOrEmailOrPhoneNumber(AccountDto accountDto) {
        Optional<Account> account = accountRepository
                .findByEmailOrUsernameOrPhoneNumber(
                        accountDto.getEmail(),
                        accountDto.getUsername(),
                        accountDto.getPhoneNumber());
        return account.map(accountMapper::apply).orElse(null);
    }

    //         Phương thức ngày chỉ sử dụng để tạo mới tài khoản STAFF
//
    @Override
    public AccountDto create(AccountDto accountDto) throws IOException {
        // Ma hoa password
        accountDto.setPassword(
                encodePassword.encodePassword(accountDto.getPassword()));

        accountDto.setRole(Role.STAFF);

        return accountMapper.apply(
                accountRepository.save(
                        accountMapper.applyToAccount(accountDto)));
    }

    //         Phương thức này để cập nhật thông tin cơ bản của tài khoản
//

    @Override
    public AccountDto updateProfile(ChangeProfileRequest changeProfileRequest, Account accountOld) throws IOException {
        changeProfileRequest.setImage(Base64.getEncoder().encodeToString(accountOld.getImage().getData()));
        String oldPassword = accountOld.getPassword();
        BeanUtils.copyProperties(changeProfileRequest, accountOld);

        if (changeProfileRequest.getPassword() != null && accountOld.getRole() == Role.ADMIN) {
            accountOld.setPassword(
                    encodePassword.encodePassword(
                            changeProfileRequest.getPassword()));
        } else
            accountOld.setPassword(oldPassword);

        if (changeProfileRequest.getImageFile() != null) {
            // Upload hình mới
            accountOld.setImage(FileConverter.convertMultipartFileToBinary(changeProfileRequest.getImageFile()));
        }
        return accountMapper.apply(accountRepository.save(accountOld));
    }

//         Phương thức này để cập nhật mật khẩu của tài khoản

    @Override
    public AccountDto changePassword(Account account, ChangePasswordDto changePasswordDto) {
        account.setPassword(encodePassword.encodePassword(changePasswordDto.getPassword()));
        return accountMapper.apply(
                accountRepository.save(account));
    }


    @Override
    public Page<AccountDto> filterRoleAndSexOrStatus(SearchCriteria searchCriteria,
                                                     Role role) {
        Page<Account> accounts =
                accountRepository.findAllByRole(role, getPageable(searchCriteria));
        System.out.println(getPageable(searchCriteria));
        return accounts.map(accountMapper::apply);
    }


    @Override
    public AccountDto findByUsernameOrEmail(String username, String email) {
        return accountRepository
                .findByUsernameOrEmail(username, email)
                .map(accountMapper::apply)
                .orElse(null);
    }


    @Override
    public AccountDto lockAndUnlockAccount(Account account, TypeAccount typeAccount) {
        account.setTypeAccount(typeAccount);
        return accountMapper.apply(accountRepository.save(account));
    }

}
