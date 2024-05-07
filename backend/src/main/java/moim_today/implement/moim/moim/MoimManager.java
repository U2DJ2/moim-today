package moim_today.implement.moim.moim;

import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingRemover;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.meeting.meeting_comment.MeetingCommentUpdater;
import moim_today.implement.moim.joined_moim.JoinedMoimAppender;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimRemover;
import moim_today.implement.schedule.schedule.ScheduleRemover;
import moim_today.implement.todo.TodoRemover;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MoimManager {

    private final JoinedMoimFinder joinedMoimFinder;
    private final JoinedMoimRemover joinedMoimRemover;
    private final TodoRemover todoRemover;
    private final MeetingFinder  meetingFinder;
    private final JoinedMeetingRemover joinedMeetingRemover;
    private final MeetingCommentUpdater meetingCommentUpdater;
    private final ScheduleRemover scheduleRemover;
    private final JoinedMoimAppender joinedMoimAppender;
    private final MoimFinder moimFinder;

    public MoimManager(final JoinedMoimFinder joinedMoimFinder,
                       final JoinedMoimRemover joinedMoimRemover,
                       final TodoRemover todoRemover,
                       final MeetingFinder meetingFinder,
                       final JoinedMeetingRemover joinedMeetingRemover,
                       final MeetingCommentUpdater meetingCommentUpdater,
                       final ScheduleRemover scheduleRemover,
                       final JoinedMoimAppender joinedMoimAppender,
                       final MoimFinder moimFinder) {
        this.joinedMoimFinder = joinedMoimFinder;
        this.joinedMoimRemover = joinedMoimRemover;
        this.todoRemover = todoRemover;
        this.meetingFinder = meetingFinder;
        this.joinedMeetingRemover = joinedMeetingRemover;
        this.meetingCommentUpdater = meetingCommentUpdater;
        this.scheduleRemover = scheduleRemover;
        this.joinedMoimAppender = joinedMoimAppender;
        this.moimFinder = moimFinder;
    }

    @Transactional
    public void deleteMemberFromMoim(final long moimId, final long memberId) {
        joinedMoimFinder.validateMemberInMoim(moimId, memberId);

        joinedMoimRemover.deleteMoimMember(moimId, memberId);
        todoRemover.deleteAllTodosCreatedByMemberInMoim(moimId, memberId);

        List<Long> meetingIds = meetingFinder.findAllByMoimId(moimId);

        joinedMeetingRemover.deleteAllByMemberInMeeting(memberId, meetingIds);
        meetingCommentUpdater.updateDeletedMembers(memberId, meetingIds);
        scheduleRemover.deleteAllByMemberInMeeting(memberId, meetingIds);
    }

    @Transactional
    public void appendMemberToMoim(final long requestMemberId, final long moimId) {
        MoimJpaEntity enterMoimEntity = moimFinder.getById(moimId);
        moimFinder.validateCapacity(enterMoimEntity);
        joinedMoimFinder.validateMemberNotInMoim(moimId, requestMemberId);

        joinedMoimAppender.createJoinedMoim(requestMemberId, moimId);
    }
}
