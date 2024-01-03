package apishop.model.mapper;

import apishop.entity.Account;
import apishop.model.request.RegisterRequest;
import apishop.service.EncodePassword;
import apishop.util.enums.Role;
import apishop.util.enums.TypeAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RegisterMapper implements Function<RegisterRequest, Account> {

    private final EncodePassword encodePassword;

    @Override
    public Account apply(RegisterRequest request) {
        return Account.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodePassword.encodePassword(request.getPassword()))
                .birthday(request.getBirthday())
                .sex(request.getSex())
                .typeAccount(TypeAccount.UNVERIFIED)
                .role(Role.CUSTOMER)
                .build();
    }
}
