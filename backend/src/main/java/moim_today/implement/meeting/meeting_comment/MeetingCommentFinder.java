package moim_today.implement.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MeetingCommentFinder {

    private final MeetingCommentRepository meetingCommentRepository;

    public MeetingCommentFinder(final MeetingCommentRepository meetingCommentRepository) {
        this.meetingCommentRepository = meetingCommentRepository;
    }

    @Transactional(readOnly = true)
    public List<MeetingCommentResponse> findAllByMeetingId(final long meetingId) {
        return meetingCommentRepository.findAllByMeetingId(meetingId);
    }

    @Transactional(readOnly = true)
    public MeetingCommentJpaEntity getById(final long meetingCommentId) {
        return meetingCommentRepository.getById(meetingCommentId);
    }
}
