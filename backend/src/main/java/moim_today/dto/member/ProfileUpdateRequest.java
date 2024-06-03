package moim_today.dto.member;

import jakarta.validation.constraints.NotBlank;

public record ProfileUpdateRequest(
        @NotBlank(message = PROFILE_IMAGE_URL_BLANK_ERROR) String imageUrl
) {
    private static final String PROFILE_IMAGE_URL_BLANK_ERROR = "프로필 사진 URL은 공백일 수 없습니다.";
}
