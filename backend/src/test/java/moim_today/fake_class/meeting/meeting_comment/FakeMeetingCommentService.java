package moim_today.fake_class.meeting.meeting_comment;

import moim_today.application.meeting.meeting_comment.MeetingCommentService;
import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;

import java.util.List;

public class FakeMeetingCommentService implements MeetingCommentService {

    @Override
    public void createMeetingComment(final long memberId, final MeetingCommentCreateRequest meetingCommentCreateRequest) {

    }

    @Override
    public List<MeetingCommentResponse> findAllByMeetingId(final long memberId, final long meetingId) {
        return List.of();
    }
}
