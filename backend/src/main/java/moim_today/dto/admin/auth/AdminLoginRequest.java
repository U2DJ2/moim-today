package moim_today.dto.admin.auth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(
        @Min(value = 0, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @NotBlank(message = ADMIN_PASSWORD_BLANK_ERROR) String adminPassword
) {
    private static final String UNIVERSITY_ID_MIN_ERROR = "잘못된 대학 ID 값이 입력 되었습니다.";
    private static final String ADMIN_PASSWORD_BLANK_ERROR = "관리자 비밀번호는 공백일 수 없습니다.";
}
