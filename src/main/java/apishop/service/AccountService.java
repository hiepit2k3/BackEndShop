package apishop.service;

import apishop.entity.Account;
import apishop.model.dto.AccountDto;
import apishop.model.dto.ChangePasswordDto;
import apishop.model.dto.SearchCriteria;
import apishop.model.request.ChangeProfileRequest;
import apishop.util.enums.Role;
import apishop.util.enums.TypeAccount;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AccountService {
    AccountDto create(AccountDto accountDto) throws IOException;

    AccountDto findByRoleAndUsernameOrEmail(Role Role, String username, String email);

    AccountDto findById(String id);

    AccountDto findByPhone(String phone);

    Page<AccountDto> findByRoleAndFullName(Role role, String fullName, SearchCriteria searchCriteria);

    AccountDto findByUsernameOrEmailOrPhoneNumber(AccountDto accountDto);

    AccountDto updateProfile(ChangeProfileRequest changeProfileRequest, Account accountOld) throws IOException;

    AccountDto changePassword(Account account, ChangePasswordDto changePasswordDto);

    Page<AccountDto> filterRoleAndSexOrStatus(SearchCriteria searchCriteria,
                                              Role role
                                             );

    AccountDto findByUsernameOrEmail(String username, String email);

    AccountDto lockAndUnlockAccount(Account account, TypeAccount typeAccount);
}
