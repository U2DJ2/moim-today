package moim_today.persistence.repository.moim.joined_meeting;

import moim_today.persistence.entity.moim.joined_meeting.JoinedMeetingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinedMeetingJpaRepository extends JpaRepository<JoinedMeetingJpaEntity, Long> {
}
