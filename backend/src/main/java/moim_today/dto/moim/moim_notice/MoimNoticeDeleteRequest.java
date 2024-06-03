package moim_today.dto.moim.moim_notice;

import jakarta.validation.constraints.Min;

public record MoimNoticeDeleteRequest(
        @Min(value = 1, message = MOIM_NOTICE_ID_MIN_ERROR) long moimNoticeId
) {
    private static final String MOIM_NOTICE_ID_MIN_ERROR = "잘못된 공지 ID 값이 입력 되었습니다.";
}
