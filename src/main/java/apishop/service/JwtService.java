package apishop.service;


import apishop.entity.Account;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface JwtService {
    Account extractAccount(String token, String password);

    String extractUsername(String token) throws IOException;

    // Tạo access token và refresh token
    String generateAccessToken(Account account);
    String generateRefreshToken(Account account);

    String generateVerifyRegisterToken(Account account);

    String generateVerifyForgotPassToken(Account account);

    // Kiểm tra token có hợp lệ hay không
    boolean isTokenValid(String token, String password);

    Account extractAndValid(String token, String password);
}
