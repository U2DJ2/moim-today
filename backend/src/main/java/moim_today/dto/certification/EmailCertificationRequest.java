package moim_today.dto.certification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailCertificationRequest(
        @Email(message = INVALID_EMAIL_FORMAT) @NotBlank(message = EMAIL_BLANK_ERROR) String email
) {
    private static final String EMAIL_BLANK_ERROR = "이메일은 공백일 수 없습니다.";
    private static final String INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다.";
}
