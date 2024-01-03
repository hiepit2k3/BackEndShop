package apishop.service.impl;

import apishop.entity.Account;
import apishop.model.dto.AccountDto;
import apishop.model.mapper.AccountMapper;
import apishop.model.mapper.RegisterMapper;
import apishop.model.request.RegisterRequest;
import apishop.model.response.JwtAuthenticationResponse;
import apishop.repository.AccountRepository;
import apishop.service.AuthenticationService;
import apishop.service.EncodePassword;
import apishop.service.JwtService;
import apishop.service.MailService;
import apishop.util.enums.TypeAccount;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static apishop.util.api.frontend.FrontEndApis.URL_FOR_GOT_PASS;
import static apishop.util.api.frontend.FrontEndApis.URL_LOGIN;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final AccountMapper accountMapper;
    private final MailService mailService;
    private final RegisterMapper registerMapper;
    private final EncodePassword encodePassword;

    /*
     *  Phương thức signup() nhận vào thông tin đăng ký của người dùng và trả về
     *  Sẽ chuyển thông tin đăng ký của người dùng thành đối tượng User và lưu vào cơ sở dữ liệu.
     *  Sau đó, sẽ tạo ra một chuỗi JWT và trả về cho người dùng.
     * */
    @Override
    @Transactional(rollbackFor = MessagingException.class)
    public String verifyEmailRegister(RegisterRequest request) throws MessagingException, IOException {
        Account account = registerMapper.apply(request);
        String token = jwtService.generateVerifyRegisterToken(account);
        account.setActivatedToken(token);
        // Đọc dữ liệu từ file hình ảnh mặc định trong resources
        ClassPathResource defaultImage = new ClassPathResource("static/image/avatar.jpg");
        byte[] image = StreamUtils.copyToByteArray(defaultImage.getInputStream());
        account.setImage(new Binary(image));
        accountRepository.save(account);
        mailService.sendVerifyEmail(account.getEmail(), token, URL_LOGIN);
        return token;
    }

    @Override
    @Transactional(rollbackFor = MessagingException.class)
    public void register(Account account) throws MessagingException {
        account.setTypeAccount(TypeAccount.ACTIVE);
        Account accountUpdate = accountRepository.save(account);
        mailService.sendWelcomeEmail(accountUpdate.getEmail(), accountUpdate.getUsername());
    }

    @Override
    public AccountDto forgotPass(Account account) throws MessagingException {
        account.setPassword(encodePassword.encodePassword(account.getPassword()));
        Account accountUpdate = accountRepository.save(account);
        mailService.sendChangePassEmail(accountUpdate.getEmail(), accountUpdate.getUsername());
        return accountMapper.apply(accountUpdate);
    }

    @Override
    public String verifyEmailForgotPass(Account account) throws MessagingException {
        String token = jwtService.generateVerifyForgotPassToken(account);
        mailService.sendVerifyEmail(account.getEmail(), token, URL_FOR_GOT_PASS);
        return token;
    }


    @Override
    public JwtAuthenticationResponse login(Account account) {
        var accessToken = jwtService.generateAccessToken(account);
        var refreshToken = jwtService.generateRefreshToken(account);
        return JwtAuthenticationResponse.apply(account, accessToken, refreshToken);
    }

    @Override
    public String getAccessToken(Account account) {
        return jwtService.generateAccessToken(account);
    }

}
