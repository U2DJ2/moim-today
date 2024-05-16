package moim_today.dto.meeting.meeting_comment;

import lombok.Builder;

@Builder
public record MeetingCommentUpdateRequest(
        long meetingCommentId,
        String contents
) {
}
