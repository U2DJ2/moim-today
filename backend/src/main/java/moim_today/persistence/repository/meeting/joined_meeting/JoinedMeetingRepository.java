package moim_today.persistence.repository.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;

import java.util.List;

public interface JoinedMeetingRepository {

    void deleteAllByMeetingIdIn(final List<Long> meetingIds);

    void save(final JoinedMeetingJpaEntity joinedMeetingJpaEntity);

    List<Long> findAllMemberIdByMeetingId(final long meetingId);

    long count();
}
