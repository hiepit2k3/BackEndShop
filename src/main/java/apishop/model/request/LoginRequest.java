package apishop.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
