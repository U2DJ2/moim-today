package moim_today.dto.certification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static moim_today.global.constant.exception.ValidationExceptionConstant.EMAIL_BLANK_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.EMAIL_INVALID_ERROR;

public record PasswordFindRequest(
        @Email(message = EMAIL_INVALID_ERROR) @NotBlank(message = EMAIL_BLANK_ERROR) String email
) {

}
