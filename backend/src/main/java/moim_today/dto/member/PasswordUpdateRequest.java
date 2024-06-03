package moim_today.dto.member;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateRequest(
        @NotBlank(message = NEW_PASSWORD_BLANK_ERROR) String newPassword
) {
    private static final String NEW_PASSWORD_BLANK_ERROR = "새로운 비밀번호는 공백일 수 없습니다.";
}
