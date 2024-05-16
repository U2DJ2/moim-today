package moim_today.persistence.repository.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;

import java.util.List;

public interface MeetingCommentRepository {

    MeetingCommentJpaEntity save(final MeetingCommentJpaEntity meetingCommentJpaEntity);

    void updateDeletedMembers(final long memberId, final List<Long> meetingIds);

    MeetingCommentJpaEntity findById(final long meetingCommentId);

    long count();

    List<MeetingCommentResponse> findAllByMeetingId(final long meetingId);

    MeetingCommentJpaEntity getById(final long meetingCommentId);
}
