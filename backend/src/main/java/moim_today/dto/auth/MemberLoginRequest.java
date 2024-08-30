package moim_today.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

public record MemberLoginRequest(
        @Email(message = EMAIL_INVALID_ERROR) @NotBlank(message = EMAIL_BLANK_ERROR) String email,
        @NotBlank(message = MEMBER_PASSWORD_BLANK_ERROR) String password,
        boolean isKeepLogin
) {

}
