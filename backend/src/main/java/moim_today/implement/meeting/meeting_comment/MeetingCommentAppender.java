package moim_today.implement.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MeetingCommentAppender {

    private final MeetingCommentRepository meetingCommentRepository;

    public MeetingCommentAppender(final MeetingCommentRepository meetingCommentRepository) {
        this.meetingCommentRepository = meetingCommentRepository;
    }

    @Transactional
    public void createMeetingComment(final long memberId, final MeetingCommentCreateRequest meetingCommentCreateRequest) {
        MeetingCommentJpaEntity meetingCommentJpaEntity = meetingCommentCreateRequest.toEntity(memberId);
        meetingCommentRepository.save(meetingCommentJpaEntity);
    }
}
