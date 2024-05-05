package moim_today.dto.moim.moim_notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MoimNoticeSimpleResponse(
        long moimNoticeId,
        String title,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

        @QueryProjection
        public MoimNoticeSimpleResponse {
        }
}
