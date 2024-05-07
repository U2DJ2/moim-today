package moim_today.dto.moim.moim_notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;

import java.time.LocalDateTime;

@Builder
public record MoimNoticeDetailResponse(
        long moimNoticeId,
        String title,
        String contents,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDateTime lastModifiedAt
) {

    public static MoimNoticeDetailResponse from(final MoimNoticeJpaEntity entity) {
        return MoimNoticeDetailResponse.builder()
                .moimNoticeId(entity.getId())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .createdAt(entity.getCreatedAt())
                .lastModifiedAt(entity.getLastModifiedAt())
                .build();
    }
}
