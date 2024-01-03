package apishop.model.response;

import apishop.entity.Account;
import apishop.util.enums.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.Date;

import static apishop.util.api.frontend.FrontEndApis.BASE_URL_FRONT_END;
import static apishop.util.api.frontend.FrontEndApis.URL_ADMIN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String image;
    private Date birthday;
    private Boolean sex;
    private TypeAccount typeAccount;
    private String accessToken;
    private String refreshToken;
    private Integer path;


    public static JwtAuthenticationResponse apply(
            Account account, String accessToken, String refreshToken) {
        return JwtAuthenticationResponse
                .builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .phoneNumber(account.getPhoneNumber())
                .address(account.getAddress())
                .image(account.getImage() != null ? Base64.getEncoder().encodeToString(account.getImage().getData()) : null)
                .birthday(account.getBirthday())
                .sex(account.getSex())
                .typeAccount(account.getTypeAccount())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .path(account.getRole().ordinal())
                .build();
    }
}
