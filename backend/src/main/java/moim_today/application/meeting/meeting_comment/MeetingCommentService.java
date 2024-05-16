package moim_today.application.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;

import java.util.List;

public interface MeetingCommentService {

    void createMeetingComment(final long memberId, final MeetingCommentCreateRequest meetingCommentCreateRequest);

    List<MeetingCommentResponse> findAllByMeetingId(final long memberId, final long meetingId);
}
