package moim_today.dto.meeting.meeting_comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MeetingCommentUpdateRequest(
        @Min(value = 0, message = MEETING_COMMENT_ID_MIN_ERROR) long meetingCommentId,
        @NotBlank(message = COMMENT_CONTENT_BLANK_ERROR) String contents
) {
    private static final String MEETING_COMMENT_ID_MIN_ERROR = "잘못된 미팅 댓글 ID 값이 입력 되었습니다.";
    private static final String COMMENT_CONTENT_BLANK_ERROR = "미팅 내용은 공백일 수 없습니다.";
}
