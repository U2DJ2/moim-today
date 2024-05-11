package moim_today.implement.meeting.joined_meeting;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class JoinedMeetingUpdater {

    private final JoinedMeetingRepository joinedMeetingRepository;

    public JoinedMeetingUpdater(final JoinedMeetingRepository joinedMeetingRepository) {
        this.joinedMeetingRepository = joinedMeetingRepository;
    }

    @Transactional
    public void updateAttendance(final long memberId, final long meetingId, final boolean attendance) {
        JoinedMeetingJpaEntity joinedMeetingJpaEntity =
                joinedMeetingRepository.findByMemberIdAndMeetingId(memberId, meetingId);
        joinedMeetingJpaEntity.updateAttendance(attendance);
    }
}
