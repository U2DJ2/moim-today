package moim_today.dto.meeting.meeting_comment;

import jakarta.validation.constraints.Min;

public record MeetingCommentDeleteRequest(
        @Min(value = 1, message = MEETING_COMMENT_ID_MIN_ERROR) long meetingCommentId
) {
    private static final String MEETING_COMMENT_ID_MIN_ERROR = "잘못된 미팅 댓글 ID 값이 입력 되었습니다.";
}
