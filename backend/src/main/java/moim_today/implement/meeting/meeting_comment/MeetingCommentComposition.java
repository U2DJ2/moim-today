package moim_today.implement.meeting.meeting_comment;

import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.dto.meeting.meeting_comment.MeetingCommentUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting_comment.MeetingCommentJpaEntity;

import java.util.List;

@Implement
public class MeetingCommentComposition {

    private final MeetingCommentAppender meetingCommentAppender;
    private final MeetingCommentFinder meetingCommentFinder;
    private final MeetingCommentUpdater meetingCommentUpdater;
    private final MeetingCommentRemover meetingCommentRemover;

    public MeetingCommentComposition(final MeetingCommentAppender meetingCommentAppender,
                                     final MeetingCommentFinder meetingCommentFinder,
                                     final MeetingCommentUpdater meetingCommentUpdater,
                                     final MeetingCommentRemover meetingCommentRemover) {
        this.meetingCommentAppender = meetingCommentAppender;
        this.meetingCommentFinder = meetingCommentFinder;
        this.meetingCommentUpdater = meetingCommentUpdater;
        this.meetingCommentRemover = meetingCommentRemover;
    }

    public void createMeetingComment(final long memberId, final MeetingCommentCreateRequest meetingCommentCreateRequest) {
        meetingCommentAppender.createMeetingComment(memberId, meetingCommentCreateRequest);
    }

    public List<MeetingCommentResponse> findAllByMeetingId(final long meetingId) {
        return meetingCommentFinder.findAllByMeetingId(meetingId);
    }

    public MeetingCommentJpaEntity getById(final long meetingCommentId) {
        return meetingCommentFinder.getById(meetingCommentId);
    }

    public void updateDeletedMembers(final long memberId, final List<Long> meetingIds) {
        meetingCommentUpdater.updateDeletedMembers(memberId, meetingIds);
    }

    public void updateMeetingComment(final long meetingCommentId,
                                     final MeetingCommentUpdateRequest meetingCommentUpdateRequest) {
        meetingCommentUpdater.updateMeetingComment(meetingCommentId, meetingCommentUpdateRequest);
    }

    public void deleteById(final long meetingCommentId) {
        meetingCommentRemover.deleteById(meetingCommentId);
    }
}
