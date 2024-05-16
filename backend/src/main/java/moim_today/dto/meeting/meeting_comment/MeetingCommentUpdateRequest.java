package moim_today.dto.meeting.meeting_comment;

public record MeetingCommentUpdateRequest(
        long meetingCommentId,
        String contents
) {
}
