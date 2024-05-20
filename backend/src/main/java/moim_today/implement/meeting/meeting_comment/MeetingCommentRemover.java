package moim_today.implement.meeting.meeting_comment;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MeetingCommentRemover {

    private final MeetingCommentRepository meetingCommentRepository;

    public MeetingCommentRemover(final MeetingCommentRepository meetingCommentRepository) {
        this.meetingCommentRepository = meetingCommentRepository;
    }

    @Transactional
    public void deleteById(final long meetingCommentId) {
        meetingCommentRepository.deleteById(meetingCommentId);
    }
}
