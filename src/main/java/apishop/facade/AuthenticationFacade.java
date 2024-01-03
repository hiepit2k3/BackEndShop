package apishop.facade;

import apishop.entity.Account;
import apishop.exception.common.DescriptionException;
import apishop.exception.core.ArchitectureException;
import apishop.exception.entity.EntityNotFoundException;
import apishop.model.dto.AccountDto;
import apishop.model.request.ForGotPassRequest;
import apishop.model.request.LoginRequest;
import apishop.model.request.RefreshTokenRequest;
import apishop.model.request.RegisterRequest;
import apishop.model.response.JwtAuthenticationResponse;
import apishop.repository.AccountRepository;
import apishop.service.AuthenticationService;
import apishop.service.JwtService;
import apishop.util.enums.TypeAccount;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final AccountFacade accountFacade;
    private final AccountRepository accountRepository;


    public void verifyEmailRegister(RegisterRequest request) throws ArchitectureException, MessagingException, IOException {
        AccountDto accountDto = accountFacade
                .findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (accountDto != null) {
            // Nếu tài khoản chưa kích hoạt và token chưa hết hạn thì báo lỗi đã tồn tại
            // Nếu hết hạn thì xóa tài khoản
            try {
                if (accountDto.getTypeAccount() == TypeAccount.UNVERIFIED &&
                        jwtService.isTokenValid(accountDto.getActivatedToken(), null)) {
                    throw new DescriptionException("Token for verify email is non expired");
                } else if (accountDto.getUsername().equals(request.getUsername())) {
                    throw new DescriptionException("Username is already exist");
                } else if (accountDto.getEmail().equals(request.getEmail())) {
                    throw new DescriptionException("Email is already exist");
                }

            } catch (ExpiredJwtException e) {
                accountRepository.deleteById(accountDto.getId());
            }
        }
        authenticationService.verifyEmailRegister(request);
    }

    public JwtAuthenticationResponse login(LoginRequest request, String token
    ) throws ArchitectureException, MessagingException {
        Account account;
        //Kiểm tra xem có token không và tài khoản có bị khóa không
        if(token != null) {
            account = jwtService.extractAndValid(token, null);

            Account accountOld = checkAccount(account.getUsername(), account.getEmail());
            if(!accountOld.isEnabled())
                authenticationService.register(accountOld);
            else if(!accountOld.isAccountNonLocked())
                throw new LockedException("User account is locked");
            account = accountOld;
        } else {
            account = checkAccount(request.getUsernameOrEmail(), request.getUsernameOrEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

        }
        return authenticationService.login(account);

    }

    public String getAccessToken(RefreshTokenRequest request) throws ArchitectureException {
        // Lấy thông tin account từ token và xem còn hạn không
        Account account = jwtService.extractAndValid(request.getRefreshToken(), null);
        // Kiểm tra trong database có account này không
        AccountDto accountDto = accountFacade
                .findByUsernameOrEmail(account.getUsername(), account.getEmail());
        if (accountDto == null) throw new EntityNotFoundException();

        return authenticationService.getAccessToken(account);
    }

    public Account checkAccount(String username, String email) throws ArchitectureException {

        Optional<Account> accountOld = accountRepository.findByUsernameOrEmail(username, email);
        if (accountOld.isEmpty()) throw new EntityNotFoundException();

        return accountOld.get();
    }

    public String verifyEmailForgotPass(String email) throws ArchitectureException, MessagingException {
        return authenticationService.verifyEmailForgotPass(checkAccount(null, email));
    }

    public AccountDto forGotPass(ForGotPassRequest request)
            throws ArchitectureException, MessagingException, IOException {
        // Lấy phần payload không cần thông qua chữ ký
        String username = jwtService.extractUsername(request.getToken());
        // Kiểm tra trong database có account này không và lấy ra password
        Account account = checkAccount(username, null);
        // Kiểm tra xem password có trùng không
        jwtService.extractAndValid(request.getToken(), account.getPassword());
        // Cập nhật password mới
        account.setPassword(request.getPassword());
        return authenticationService.forgotPass(account);
    }
}
