package moim_today.persistence.repository.meeting.meeting;

import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.util.List;

public interface MeetingRepository {

    List<Long> findAllByMoimId(final long moimId);

    MeetingJpaEntity getById(final long meetingId);

    MeetingJpaEntity save(final MeetingJpaEntity meetingJpaEntity);

    long count();
}
