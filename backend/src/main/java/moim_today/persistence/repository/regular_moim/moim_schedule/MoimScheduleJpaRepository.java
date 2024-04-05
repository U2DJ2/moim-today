package moim_today.persistence.repository.regular_moim.moim_schedule;

import moim_today.persistence.entity.regular_moim.moim_schedule.MoimScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimScheduleJpaRepository extends JpaRepository<MoimScheduleJpaEntity, Long> {
}
