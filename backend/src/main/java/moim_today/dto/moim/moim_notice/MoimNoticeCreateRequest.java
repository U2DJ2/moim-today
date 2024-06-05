package moim_today.dto.moim.moim_notice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record MoimNoticeCreateRequest(
        @Min(value = 0, message = MOIM_ID_MIN_ERROR) long moimId,
        @NotBlank(message = MOIM_NOTICE_TITLE_BLANK_ERROR) String title,
        @NotBlank(message = MOIM_NOTICE_CONTENT_BLANK_ERROR) String contents
) {

    public MoimNoticeJpaEntity toEntity() {
        return MoimNoticeJpaEntity.builder()
                .moimId(moimId)
                .title(title)
                .contents(contents)
                .build();
    }
}
