package moim_today.dto.meeting.meeting_comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static moim_today.global.constant.exception.ValidationExceptionConstant.MEETING_COMMENT_CONTENT_BLANK_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.MEETING_COMMENT_ID_MIN_ERROR;

@Builder
public record MeetingCommentUpdateRequest(
        @Min(value = 0, message = MEETING_COMMENT_ID_MIN_ERROR) long meetingCommentId,
        @NotBlank(message = MEETING_COMMENT_CONTENT_BLANK_ERROR) String contents
) {
}
