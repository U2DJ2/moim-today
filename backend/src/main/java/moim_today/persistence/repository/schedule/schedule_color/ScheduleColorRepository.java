package moim_today.persistence.repository.schedule.schedule_color;

import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;

import java.util.Optional;

public interface ScheduleColorRepository {

    Optional<ScheduleColorJpaEntity> findByMemberId(final long memberId);

    void save(final ScheduleColorJpaEntity scheduleColorJpaEntity);
}
