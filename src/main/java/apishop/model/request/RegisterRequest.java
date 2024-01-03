package apishop.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    @NonNull
    private String username;
    @NonNull
    private String fullName;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private Date birthday;
    @NonNull
    private Boolean sex;

}
