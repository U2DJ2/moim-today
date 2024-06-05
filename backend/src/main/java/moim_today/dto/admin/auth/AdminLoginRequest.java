package moim_today.dto.admin.auth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import static moim_today.global.constant.exception.ValidationExceptionConstant.ADMIN_PASSWORD_BLANK_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.UNIVERSITY_ID_MIN_ERROR;


public record AdminLoginRequest(
        @Min(value = 0, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @NotBlank(message = ADMIN_PASSWORD_BLANK_ERROR) String adminPassword
) {

}
