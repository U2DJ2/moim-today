package moim_today.implement.meeting.meeting;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MeetingAppender {

    private final MeetingRepository meetingRepository;

    public MeetingAppender(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional
    public MeetingJpaEntity saveMeeting(final MeetingJpaEntity meetingJpaEntity) {
        return meetingRepository.save(meetingJpaEntity);
    }
}
