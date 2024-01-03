package apishop.controller;

import apishop.exception.core.ArchitectureException;
import apishop.facade.AuthenticationFacade;
import apishop.model.common.ResponseHandler;
import apishop.model.request.ForGotPassRequest;
import apishop.model.request.LoginRequest;
import apishop.model.request.RefreshTokenRequest;
import apishop.model.request.RegisterRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static apishop.util.api.ConstantsApi.Auth.AUTH_PATH;


@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationFacade authenticationFacade;

    /**
     * Only user can register
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request)
            throws ArchitectureException, MessagingException, IOException {
        authenticationFacade.verifyEmailRegister(request);
        return ResponseHandler.response(HttpStatus.OK,"Please check your email to verify account", true);
    }

    /**
     * Anyone can log in
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody(required = false) LoginRequest request,
                                        @RequestParam(required = false) String token)
            throws ArchitectureException, MessagingException {
        return ResponseHandler.response(HttpStatus.OK, authenticationFacade.login(request, token), true);
    }

    /**
     * Anyone can get new access token
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                authenticationFacade.getAccessToken(request), true);
    }

    /**
     * Anyone can access
     */

    @GetMapping("/verify-email")
    public ResponseEntity<Object> verifyEmail(@RequestParam String email)
            throws ArchitectureException, MessagingException {
        return ResponseHandler.response(HttpStatus.OK,
                authenticationFacade.verifyEmailForgotPass(email), true);
    }

    /**
     * Anyone can access
     */

    @PostMapping("/for-got-password")
    public ResponseEntity<Object> forGotPassword(@RequestBody ForGotPassRequest request)
            throws ArchitectureException, MessagingException, IOException {
        return ResponseHandler.response(HttpStatus.OK,
                authenticationFacade.forGotPass(request), true);
    }

}
