package apishop.model.mapper;

import apishop.entity.Account;
import apishop.model.dto.AccountDto;
import apishop.util.FileConverter;
import apishop.util.enums.TypeAccount;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AccountMapper implements Function<Account, AccountDto> {

    @Override
    public AccountDto apply(Account account) {
        return AccountDto
                .builder()
                .id(account.getId())
                .image(Base64.getEncoder().encodeToString(account.getImage().getData()))
                .email(account.getEmail())
                .address(account.getAddress())
                .activatedToken(account.getActivatedToken())
                .birthday(account.getBirthday())
                .sex(account.getSex())
                .phoneNumber(account.getPhoneNumber())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .typeAccount(account.getTypeAccount())
                .lastAccessDate(account.getLastAccessDate())
                .build();
    }

    public Account applyToAccount(AccountDto accountDto) throws IOException {
        Binary image = FileConverter.convertMultipartFileToBinary(accountDto.getImageFile());
        return Account
                .builder()
                .id(accountDto.getId())
                .image(image)
                .email(accountDto.getEmail())
                .address(accountDto.getAddress())
                .password(accountDto.getPassword())
                .activatedToken(accountDto.getActivatedToken())
                .birthday(accountDto.getBirthday())
                .sex(accountDto.getSex())
                .phoneNumber(accountDto.getPhoneNumber())
                .role(accountDto.getRole())
                .username(accountDto.getUsername())
                .fullName(accountDto.getFullName())
                .password(accountDto.getPassword())
                .typeAccount(accountDto.getTypeAccount() == null ?
                        TypeAccount.ACTIVE :
                        accountDto.getTypeAccount())
                .lastAccessDate(accountDto.getLastAccessDate())
                .build();
    }



}
