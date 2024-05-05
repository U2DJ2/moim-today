package moim_today.persistence.repository.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinedMeetingJpaRepository extends JpaRepository<JoinedMeetingJpaEntity, Long> {

    void deleteAllByMeetingIdIn(final List<Long> meetingIds);

    JoinedMeetingJpaEntity findById(final long joinedMeetingId);
}
