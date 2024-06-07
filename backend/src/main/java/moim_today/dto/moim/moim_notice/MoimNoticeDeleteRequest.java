package moim_today.dto.moim.moim_notice;

import jakarta.validation.constraints.Min;

import static moim_today.global.constant.exception.ValidationExceptionConstant.MOIM_NOTICE_ID_MIN_ERROR;

public record MoimNoticeDeleteRequest(
        @Min(value = 0, message = MOIM_NOTICE_ID_MIN_ERROR) long moimNoticeId
) {

}
