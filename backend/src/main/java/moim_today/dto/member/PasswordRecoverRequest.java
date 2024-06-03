package moim_today.dto.member;

import jakarta.validation.constraints.NotBlank;

public record PasswordRecoverRequest(
        @NotBlank(message = PASSWORD_TOKEN_BLANK_ERROR) String passwordToken,
        @NotBlank(message = NEW_PASSWORD_BLANK_ERROR) String newPassword
) {
    private static final String PASSWORD_TOKEN_BLANK_ERROR = "비밀번호 토큰은 공백일 수 없습니다.";
    private static final String NEW_PASSWORD_BLANK_ERROR = "새로운 비밀번호는 공백일 수 없습니다.";
}
