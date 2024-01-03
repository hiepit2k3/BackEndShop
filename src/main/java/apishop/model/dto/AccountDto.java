package apishop.model.dto;

import apishop.util.enums.Role;
import apishop.util.enums.TypeAccount;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor(force = true)
public class AccountDto {

    private String id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String image;
    private MultipartFile imageFile;
    private Date birthday;
    private Boolean sex;
    private String activatedToken;
    private TypeAccount typeAccount;
    private Date lastAccessDate;
    private Role role;

    public AccountDto(String email) {
        this.email = email;
    }
}
