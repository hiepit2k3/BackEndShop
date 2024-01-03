package apishop.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
// Lớp này dùng để thay đổi thông tin cơ bản của người dùng trừ username và email
public class ChangeProfileRequest {
    @NonNull
    private String fullName;
    private String phoneNumber;
    private String address;
    private String password;
    @NonNull
    private Date birthday;
    @NonNull
    private Boolean sex;
    private String image;
    private MultipartFile imageFile;

}
