package moim_today.persistence.repository.meeting.meeting;

import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository {

    List<Long> findMeetingIdsByMoimId(final long moimId);

    List<MeetingJpaEntity> findAllByMoimId(final long moimId, final LocalDateTime currentDateTime);

    List<MeetingJpaEntity> findAllUpcomingByMoimId(final long moimId, final LocalDateTime currentDateTime);

    List<MeetingJpaEntity> findAllPastByMoimId(final long moimId, final LocalDateTime currentDateTime);

    MeetingJpaEntity getById(final long meetingId);

    MeetingJpaEntity save(final MeetingJpaEntity meetingJpaEntity);

    long count();
}
