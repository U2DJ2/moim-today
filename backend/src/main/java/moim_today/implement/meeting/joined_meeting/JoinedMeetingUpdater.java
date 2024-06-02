package moim_today.implement.meeting.joined_meeting;

import lombok.extern.slf4j.Slf4j;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Implement
public class JoinedMeetingUpdater {

    private final JoinedMeetingRepository joinedMeetingRepository;

    public JoinedMeetingUpdater(final JoinedMeetingRepository joinedMeetingRepository) {
        this.joinedMeetingRepository = joinedMeetingRepository;
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
        log.info("update joinedMeetingId = {}", joinedMeetingJpaEntity.isUpcomingNoticeSent());
    }
}
