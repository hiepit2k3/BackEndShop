package apishop.service;


import apishop.entity.Account;
import apishop.model.dto.AccountDto;
import apishop.model.request.RegisterRequest;
import apishop.model.response.JwtAuthenticationResponse;
import jakarta.mail.MessagingException;

import java.io.IOException;

// Lớp này sẽ trả về token cho người dùng sau khi đăng ký hoặc đăng nhập thành công
public interface AuthenticationService {
    String verifyEmailRegister(RegisterRequest request) throws MessagingException, IOException;

    void register(Account account) throws MessagingException, MessagingException;

    AccountDto forgotPass(Account account) throws MessagingException;

    String verifyEmailForgotPass(Account account) throws MessagingException;

    JwtAuthenticationResponse login(Account account);

    String getAccessToken(Account account);


}
