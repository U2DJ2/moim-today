package moim_today.implement.meeting.joined_meeting;

import moim_today.global.annotation.Implement;
import moim_today.implement.schedule.schedule.ScheduleAppender;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class JoinedMeetingUpdater {

    private final JoinedMeetingRepository joinedMeetingRepository;
    private final ScheduleAppender scheduleAppender;

    public JoinedMeetingUpdater(final JoinedMeetingRepository joinedMeetingRepository,
                                final ScheduleAppender scheduleAppender) {
        this.joinedMeetingRepository = joinedMeetingRepository;
        this.scheduleAppender = scheduleAppender;
    }

    @Transactional
    public void updateAttendance(final long memberId, final long meetingId, final boolean attendance) {
        JoinedMeetingJpaEntity joinedMeetingJpaEntity =
                joinedMeetingRepository.getByMemberIdAndMeetingId(memberId, meetingId);
        joinedMeetingJpaEntity.updateAttendance(attendance);
    }

    @Transactional
    public void updateUpcomingNoticeSent(final long joinedMeetingId, final boolean upcomingNoticeSent) {
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = joinedMeetingRepository.getById(joinedMeetingId);
        joinedMeetingJpaEntity.updateUpcomingNoticeSent(upcomingNoticeSent);
    }
}
