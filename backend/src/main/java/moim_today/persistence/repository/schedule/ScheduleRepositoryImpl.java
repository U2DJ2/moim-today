package moim_today.persistence.repository.schedule;

import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    public ScheduleRepositoryImpl(final ScheduleJpaRepository scheduleJpaRepository) {
        this.scheduleJpaRepository = scheduleJpaRepository;
    }
}
