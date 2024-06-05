package moim_today.dto.member;

import jakarta.validation.constraints.NotBlank;

import static moim_today.global.constant.exception.ValidationExceptionConstant.PROFILE_IMAGE_URL_BLANK_ERROR;

public record ProfileUpdateRequest(
        @NotBlank(message = PROFILE_IMAGE_URL_BLANK_ERROR) String imageUrl
) {

}
