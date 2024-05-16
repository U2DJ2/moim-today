package moim_today.dto.meeting.meeting_comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MeetingCommentResponse(
        long meetingCommentId,
        String username,
        String imageUrl,
        String contents,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

        @QueryProjection
        public MeetingCommentResponse {
        }
}
