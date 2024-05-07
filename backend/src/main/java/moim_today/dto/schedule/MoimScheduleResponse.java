package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import moim_today.domain.member.Member;

import java.time.LocalDateTime;

@Builder
public record MoimScheduleResponse(
        long scheduleId,
        long meetingId,
        long memberId,
        String username,
        String memberProfileImageUrl,
        String scheduleName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime
) {
        @QueryProjection
        public MoimScheduleResponse {
        }

        public Member toDomain() {
                return Member.builder()
                        .memberId(memberId)
                        .username(username)
                        .memberProfileImageUrl(memberProfileImageUrl)
                        .build();
        }
}
