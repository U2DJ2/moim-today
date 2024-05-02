package moim_today.persistence.repository.schedule.schedule_color;

import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleColorJpaRepository extends JpaRepository<ScheduleColorJpaEntity, Long> {

    Optional<ScheduleColorJpaEntity> findByMemberId(final long memberId);
}
