package moim_today.dto.meeting.meeting_comment;

import jakarta.validation.constraints.Min;

import static moim_today.global.constant.exception.ValidationExceptionConstant.MEETING_COMMENT_ID_MIN_ERROR;

public record MeetingCommentDeleteRequest(
        @Min(value = 0, message = MEETING_COMMENT_ID_MIN_ERROR) long meetingCommentId
) {

}
