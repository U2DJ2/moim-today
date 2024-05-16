package moim_today.implement.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;
import moim_today.persistence.repository.meeting.meeting_comment.MeetingCommentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MeetingCommentUpdater {

    private final MeetingCommentRepository meetingCommentRepository;

    public MeetingCommentUpdater(final MeetingCommentRepository meetingCommentRepository) {
        this.meetingCommentRepository = meetingCommentRepository;
    }

    @Transactional
    public void updateDeletedMembers(final long memberId, final List<Long> meetingIds) {
        meetingCommentRepository.updateDeletedMembers(memberId, meetingIds);
    }

    @Transactional
    public void updateMeetingComment(final MeetingCommentJpaEntity meetingCommentJpaEntity, final MeetingCommentUpdateRequest meetingCommentUpdateRequest) {
        meetingCommentJpaEntity.updateMeetingComment(meetingCommentUpdateRequest);
    }
}
