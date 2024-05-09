package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Implement
public class MeetingFinder {

    private final MeetingRepository meetingRepository;

    public MeetingFinder(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional(readOnly = true)
    public List<Long> findMeetingIdsByMoimId(final long moimId) {
        return meetingRepository.findMeetingIdsByMoimId(moimId);
    }

    @Transactional(readOnly = true)
    public List<MeetingJpaEntity> findAllByMoimId(final long moimId, final MeetingStatus meetingStatus,
                                                  final LocalDateTime currentDateTime) {
        if (meetingStatus.equals(MeetingStatus.PAST)) {
            return meetingRepository.findAllPastByMoimId(moimId, currentDateTime);
        }

        return meetingRepository.findAllUpcomingByMoimId(moimId, currentDateTime);
    }
}
