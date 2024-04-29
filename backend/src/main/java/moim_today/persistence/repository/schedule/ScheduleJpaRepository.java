package moim_today.persistence.repository.schedule;

import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, Long> {

    List<ScheduleJpaEntity> findAllByMemberId(final long memberId);
}
