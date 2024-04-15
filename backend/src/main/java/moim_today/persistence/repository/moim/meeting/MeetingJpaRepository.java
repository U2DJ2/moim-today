package moim_today.persistence.repository.moim.meeting;

import moim_today.persistence.entity.moim.meeting.MeetingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingJpaRepository extends JpaRepository<MeetingJpaEntity, Long> {
}
