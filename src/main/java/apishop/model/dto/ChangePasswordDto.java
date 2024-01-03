package apishop.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ChangePasswordDto {

    private String oldPassword;
    private String password;

}
