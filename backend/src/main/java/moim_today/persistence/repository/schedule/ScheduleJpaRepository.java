package moim_today.persistence.repository.schedule;

import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, Long> {
}
