package moim_today.implement.moim.moim;

import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingRemover;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.meeting.meeting_comment.MeetingCommentUpdater;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimRemover;
import moim_today.implement.schedule.schedule.ScheduleRemover;
import moim_today.implement.todo.TodoRemover;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.NumberConstant.MINUS_ONE;

@Implement
public class MoimRemover {

    private final MoimRepository moimRepository;
    private final JoinedMoimFinder joinedMoimFinder;
    private final JoinedMoimRemover joinedMoimRemover;
    private final MoimUpdater moimUpdater;
    private final TodoRemover todoRemover;
    private final MeetingFinder meetingFinder;
    private final JoinedMeetingRemover joinedMeetingRemover;
    private final MeetingCommentUpdater meetingCommentUpdater;
    private final ScheduleRemover scheduleRemover;

    public MoimRemover(final MoimRepository moimRepository, final JoinedMoimFinder joinedMoimFinder,
                       final JoinedMoimRemover joinedMoimRemover, final MoimUpdater moimUpdater,
                       final TodoRemover todoRemover, final MeetingFinder meetingFinder,
                       final JoinedMeetingRemover joinedMeetingRemover, final MeetingCommentUpdater meetingCommentUpdater,
                       final ScheduleRemover scheduleRemover) {
        this.moimRepository = moimRepository;
        this.joinedMoimFinder = joinedMoimFinder;
        this.joinedMoimRemover = joinedMoimRemover;
        this.moimUpdater = moimUpdater;
        this.todoRemover = todoRemover;
        this.meetingFinder = meetingFinder;
        this.joinedMeetingRemover = joinedMeetingRemover;
        this.meetingCommentUpdater = meetingCommentUpdater;
        this.scheduleRemover = scheduleRemover;
    }

    @Transactional
    public void deleteById(final long moimId) {
        moimRepository.deleteById(moimId);
    }

    @Transactional
    public void deleteMoim(final long memberId, final long moimId) {
        MoimJpaEntity moimJpaEntity =  moimRepository.getById(moimId);
        moimJpaEntity.validateHostMember(memberId);

        joinedMoimRemover.deleteAllByMoimId(moimId);
        todoRemover.deleteAllByMoimId(moimId);
        moimRepository.deleteById(moimId);

        List<Long> meetingIds = meetingFinder.findMeetingIdsByMoimId(moimId);

        joinedMeetingRemover.deleteAllByMeetingIdIn(meetingIds);
        scheduleRemover.deleteAllByMeetingIdIn(meetingIds);
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
}
