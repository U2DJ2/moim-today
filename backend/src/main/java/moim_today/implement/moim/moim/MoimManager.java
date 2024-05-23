package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingAppender;
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

import java.time.LocalDate;
import java.util.List;

@Implement
public class MoimManager {

    private final JoinedMoimFinder joinedMoimFinder;
    private final JoinedMoimRemover joinedMoimRemover;
    private final TodoRemover todoRemover;
    private final MeetingFinder meetingFinder;
    private final JoinedMeetingRemover joinedMeetingRemover;
    private final JoinedMeetingAppender joinedMeetingAppender;
    private final MeetingCommentUpdater meetingCommentUpdater;
    private final ScheduleRemover scheduleRemover;
    private final JoinedMoimAppender joinedMoimAppender;
    private final MoimFinder moimFinder;

    public MoimManager(final JoinedMoimFinder joinedMoimFinder,
                       final JoinedMoimRemover joinedMoimRemover,
                       final TodoRemover todoRemover,
                       final MeetingFinder meetingFinder,
                       final JoinedMeetingRemover joinedMeetingRemover,
                       final JoinedMeetingAppender joinedMeetingAppender,
                       final MeetingCommentUpdater meetingCommentUpdater,
                       final ScheduleRemover scheduleRemover,
                       final JoinedMoimAppender joinedMoimAppender,
                       final MoimFinder moimFinder) {
        this.joinedMoimFinder = joinedMoimFinder;
        this.joinedMoimRemover = joinedMoimRemover;
        this.todoRemover = todoRemover;
        this.meetingFinder = meetingFinder;
        this.joinedMeetingRemover = joinedMeetingRemover;
        this.joinedMeetingAppender = joinedMeetingAppender;
        this.meetingCommentUpdater = meetingCommentUpdater;
        this.scheduleRemover = scheduleRemover;
        this.joinedMoimAppender = joinedMoimAppender;
        this.moimFinder = moimFinder;
    }

    @Transactional
    public void deleteMemberFromMoim(final long memberId, final long moimId) {

        joinedMoimFinder.validateMemberInMoim(memberId, moimId);

        joinedMoimRemover.deleteMoimMember(moimId, memberId);
        todoRemover.deleteAllTodosCreatedByMemberInMoim(moimId, memberId);

        List<Long> meetingIds = meetingFinder.findMeetingIdsByMoimId(moimId);

        joinedMeetingRemover.deleteAllByMemberInMeeting(memberId, meetingIds);
        meetingCommentUpdater.updateDeletedMembers(memberId, meetingIds);
        scheduleRemover.deleteAllByMemberInMeeting(memberId, meetingIds);
    }

    @Transactional
    public void appendMemberToMoim(final long requestMemberId, final long moimId) {
        MoimJpaEntity enterMoimEntity = moimFinder.getByIdWithPessimisticLock(moimId);
        moimFinder.validateCapacity(enterMoimEntity);
        joinedMoimFinder.validateMemberNotInMoim(moimId, requestMemberId);

        joinedMoimAppender.createJoinedMoim(requestMemberId, moimId);
        List<Long> meetingIds = meetingFinder.findMeetingIdsByMoimId(moimId);
        meetingIds.forEach(meetingId -> joinedMeetingAppender.saveJoinedMeeting(moimId, meetingId));
    }

    @Transactional(readOnly = true)
    public boolean isHost(final long memberId, final long moimId) {
        return moimFinder.isHost(memberId, moimId);
    }

    @Transactional(readOnly = true)
    public boolean isJoinedMoim(final long moimId, final long memberId) {
        return joinedMoimFinder.isJoining(moimId, memberId);
    }

    @Transactional(readOnly = true)
    public String getTitleById(final Long moimId) {
        return moimFinder.getTitleById(moimId);
    }

    @Transactional(readOnly = true)
    public List<MoimSimpleResponse> findAllMyMoimSimpleResponses(final long hostMemberId, final LocalDate now, final boolean ended) {
        return moimFinder.findAllMyMoimSimpleResponses(hostMemberId, now, ended);
    }

    @Transactional(readOnly = true)
    public List<MoimSimpleResponse> findAllMyJoinedMoimSimpleResponses(final long memberId, final LocalDate now, final boolean ended) {
        return joinedMoimFinder.findAllMyJoinedMoimSimpleResponses(memberId, now, ended);
    }
}
