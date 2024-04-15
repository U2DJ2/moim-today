package moim_today.persistence.repository.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinedMeetingJpaRepository extends JpaRepository<JoinedMeetingJpaEntity, Long> {
}
