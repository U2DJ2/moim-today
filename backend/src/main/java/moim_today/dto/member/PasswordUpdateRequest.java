package moim_today.dto.member;

import jakarta.validation.constraints.NotBlank;

import static moim_today.global.constant.exception.ValidationExceptionConstant.NEW_PASSWORD_BLANK_ERROR;

public record PasswordUpdateRequest(
        @NotBlank(message = NEW_PASSWORD_BLANK_ERROR) String newPassword
) {

}
