package moim_today.implement.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentRepository;

import java.util.List;

@Implement
public class MeetingCommentFinder {

    private final MeetingCommentRepository meetingCommentRepository;

    public MeetingCommentFinder(final MeetingCommentRepository meetingCommentRepository) {
        this.meetingCommentRepository = meetingCommentRepository;
    }

    public List<MeetingCommentResponse> findAllByMeetingId(final long meetingId) {
        return meetingCommentRepository.findAllByMeetingId(meetingId);
    }
}
