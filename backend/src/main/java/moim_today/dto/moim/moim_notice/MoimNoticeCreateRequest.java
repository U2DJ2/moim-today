package moim_today.dto.moim.moim_notice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;

@Builder
public record MoimNoticeCreateRequest(
        @Min(value = 0, message = MOIM_NOTICE_ID_MIN_ERROR) long moimId,
        @NotBlank(message = MOIM_NOTICE_TITLE_BLANK_ERROR) String title,
        @NotBlank(message = MOIM_NOTICE_CONTENT_BLANK_ERROR) String contents
) {
    private static final String MOIM_NOTICE_ID_MIN_ERROR = "잘못된 모임 ID 값이 입력 되었습니다.";
    private static final String MOIM_NOTICE_TITLE_BLANK_ERROR = "공지 제목은 공백일 수 없습니다.";
    private static final String MOIM_NOTICE_CONTENT_BLANK_ERROR = "공지 내용은 공백일 수 없습니다.";

    public MoimNoticeJpaEntity toEntity() {
        return MoimNoticeJpaEntity.builder()
                .moimId(moimId)
                .title(title)
                .contents(contents)
                .build();
    }
}
