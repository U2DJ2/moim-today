package moim_today.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
        @Email(message = INVALID_EMAIL_FORMAT) @NotBlank(message = EMAIL_BLANK_ERROR) String email,
        @NotBlank(message = PASSWORD_BLANK_ERROR) String password,
        boolean isKeepLogin
) {

    private static final String EMAIL_BLANK_ERROR = "이메일은 공백일 수 없습니다.";
    private static final String INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다.";
    private static final String PASSWORD_BLANK_ERROR = "비밀번호는 공백일 수 없습니다.";

}
