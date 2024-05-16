package moim_today.application.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;

public interface MeetingCommentService {

    void createMeetingComment(final long memberId, final MeetingCommentCreateRequest meetingCommentCreateRequest);
}
