package moim_today.implement.moim.moim;

import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingRemover;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimRemover;
import moim_today.implement.schedule.ScheduleDeleter;
import moim_today.implement.todo.TodoRemover;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MoimRemover {

    private final MoimRepository moimRepository;

    private final MeetingFinder meetingFinder;
    private final MoimFinder moimFinder;
    private final JoinedMeetingRemover joinedMeetingRemover;
    private final TodoRemover todoRemover;
    private final JoinedMoimRemover joinedMoimRemover;
    private final ScheduleDeleter scheduleDeleter;



    public MoimRemover(final MoimRepository moimRepository,
                       final MeetingFinder meetingFinder, final MoimFinder moimFinder, final TodoRemover todoRemover,
                       final JoinedMoimRemover joinedMoimRemover,
                       final JoinedMeetingRemover joinedMeetingRemover,
                       final ScheduleDeleter scheduleDeleter) {
        this.moimRepository = moimRepository;
        this.meetingFinder = meetingFinder;
        this.moimFinder = moimFinder;
        this.todoRemover = todoRemover;
        this.joinedMoimRemover = joinedMoimRemover;
        this.joinedMeetingRemover = joinedMeetingRemover;
        this.scheduleDeleter = scheduleDeleter;
    }

    @Transactional
    public void deleteMoim(final long memberId, final long moimId) {
        MoimJpaEntity moimJpaEntity =  moimFinder.getById(moimId);
        moimJpaEntity.validateMember(memberId);

        joinedMoimRemover.deleteAllByMoimId(moimId);
        todoRemover.deleteAllByMoimId(moimId);

        List<Long> meetingIds = meetingFinder.findAllByMoimId(moimId);

        if (!meetingIds.isEmpty()) {
            joinedMeetingRemover.deleteAllByMeetingIdIn(meetingIds);
            scheduleDeleter.deleteAllByMeetingIdIn(meetingIds);
        }

        moimRepository.delete(moimJpaEntity);
    }
}
