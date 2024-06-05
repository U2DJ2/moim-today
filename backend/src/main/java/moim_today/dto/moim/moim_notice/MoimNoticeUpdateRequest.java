package moim_today.dto.moim.moim_notice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record MoimNoticeUpdateRequest(
        @Min(value = 0, message = MOIM_NOTICE_ID_MIN_ERROR) long moimNoticeId,
        @NotBlank(message = MOIM_NOTICE_TITLE_BLANK_ERROR) String title,
        @NotBlank(message = MOIM_NOTICE_CONTENT_BLANK_ERROR) String contents
) {

}
