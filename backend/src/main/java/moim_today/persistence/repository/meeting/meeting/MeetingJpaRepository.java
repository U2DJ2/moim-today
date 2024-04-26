package moim_today.persistence.repository.meeting.meeting;

import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingJpaRepository extends JpaRepository<MeetingJpaEntity, Long> {
}
