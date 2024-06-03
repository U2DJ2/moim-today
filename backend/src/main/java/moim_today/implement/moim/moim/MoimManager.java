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
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static moim_today.global.constant.NumberConstant.MINUS_ONE;
import static moim_today.global.constant.NumberConstant.PLUS_ONE;

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
    private final MoimUpdater moimUpdater;

    public MoimManager(final JoinedMoimFinder joinedMoimFinder,
                       final JoinedMoimRemover joinedMoimRemover,
                       final TodoRemover todoRemover,
                       final MeetingFinder meetingFinder,
                       final JoinedMeetingRemover joinedMeetingRemover,
                       final JoinedMeetingAppender joinedMeetingAppender,
                       final MeetingCommentUpdater meetingCommentUpdater,
                       final ScheduleRemover scheduleRemover,
                       final JoinedMoimAppender joinedMoimAppender,
                       final MoimFinder moimFinder,
                       final MoimUpdater moimUpdater) {
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
        this.moimUpdater = moimUpdater;
    }

    @Transactional
    public void deleteMemberFromMoim(final long memberId, final long moimId) {

        joinedMoimFinder.validateMemberInMoim(memberId, moimId);

        joinedMoimRemover.deleteMoimMember(moimId, memberId);
        moimUpdater.updateMoimCurrentCount(moimId, MINUS_ONE.value());
        todoRemover.deleteAllTodosCreatedByMemberInMoim(moimId, memberId);

        List<Long> meetingIds = meetingFinder.findMeetingIdsByMoimId(moimId);

        joinedMeetingRemover.deleteAllByMemberInMeeting(memberId, meetingIds);
        meetingCommentUpdater.updateDeletedMembers(memberId, meetingIds);
        scheduleRemover.deleteAllByMemberInMeeting(memberId, meetingIds);
    }

    @Transactional
    public void appendMemberToMoim(final long requestMemberId, final long moimId, final LocalDate currentDate) {
        MoimJpaEntity enterMoimEntity = moimFinder.getByIdWithPessimisticLock(moimId);
        enterMoimEntity.validateMoimNotEnd(currentDate);
        enterMoimEntity.validatePublic();
        moimFinder.validateCapacity(enterMoimEntity);
        joinedMoimFinder.validateMemberNotInMoim(moimId, requestMemberId);

        moimUpdater.updateMoimCurrentCount(moimId, PLUS_ONE.value());
        joinedMoimAppender.createJoinedMoim(requestMemberId, moimId);

        List<Long> meetingIds = meetingFinder.findUpcomingMeetingIdsByMoimId(moimId, currentDate);
        for (long meetingId : meetingIds) {
            MeetingJpaEntity meetingJpaEntity = meetingFinder.getById(meetingId);
            joinedMeetingAppender.saveJoinedMeeting(moimId, meetingId, enterMoimEntity.getTitle(), meetingJpaEntity);
        }
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
    public List<MoimSimpleResponse> findAllJoinedMoimSimpleResponseByEndStatus(final long memberId, final LocalDate now, final boolean ended) {
        List<Long> joinedMoims = joinedMoimFinder.findMoimIdsByMemberId(memberId);
        return getMoimSimpleResponses(joinedMoims, now, ended);
    }

    @Transactional(readOnly = true)
    public List<MoimSimpleResponse> findAllHostMoimSimpleResponsesByEndStatus(final long hostMemberId, final LocalDate now, final boolean ended) {
        List<Long> joinedMoims = joinedMoimFinder.findMoimIdsByMemberId(hostMemberId);
        List<Long> hostMoims = joinedMoims.stream()
                .filter(moimId -> moimFinder.isHost(hostMemberId, moimId))
                .toList();
        return getMoimSimpleResponses(hostMoims, now, ended);
    }

    @Transactional(readOnly = true)
    public List<MoimSimpleResponse> getMoimSimpleResponses(final List<Long> joinedMoims, final LocalDate now, final boolean ended) {
        if (ended) {
            return moimFinder.findEndedMoimSimpleResponsesByMoimIds(joinedMoims, now);
        }
        return moimFinder.findInProgressMoimSimpleResponsesByMoimIds(joinedMoims, now);
    }

    @Transactional
    public void appendMemberToPrivateMoim(final long requestMemberId, final long moimId, final String password, final LocalDate currentDate) {
        MoimJpaEntity enterMoimEntity = moimFinder.getByIdWithPessimisticLock(moimId);
        enterMoimEntity.validateMoimNotEnd(currentDate);
        enterMoimEntity.validatePassword(password);
        moimFinder.validateCapacity(enterMoimEntity);
        joinedMoimFinder.validateMemberNotInMoim(moimId, requestMemberId);

        moimUpdater.updateMoimCurrentCount(moimId, PLUS_ONE.value());
        joinedMoimAppender.createJoinedMoim(requestMemberId, moimId);

        List<Long> meetingIds = meetingFinder.findUpcomingMeetingIdsByMoimId(moimId, currentDate);
        for (long meetingId : meetingIds) {
            MeetingJpaEntity meetingJpaEntity = meetingFinder.getById(meetingId);
            joinedMeetingAppender.saveJoinedMeeting(moimId, meetingId, enterMoimEntity.getTitle(), meetingJpaEntity);
        }
    }
}
