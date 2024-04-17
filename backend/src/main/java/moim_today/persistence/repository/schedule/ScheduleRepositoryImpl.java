package moim_today.persistence.repository.schedule;

import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    public ScheduleRepositoryImpl(final ScheduleJpaRepository scheduleJpaRepository) {
        this.scheduleJpaRepository = scheduleJpaRepository;
    }

    @Override
    public void save(final ScheduleJpaEntity scheduleJpaEntity) {
        scheduleJpaRepository.save(scheduleJpaEntity);
    }
}
