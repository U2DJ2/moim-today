package moim_today.persistence.repository.schedule.schedule_color;

import org.springframework.stereotype.Repository;

@Repository
public class ScheduleColorRepositoryImpl implements ScheduleColorRepository {

    private final ScheduleColorJpaRepository scheduleColorJpaRepository;

    public ScheduleColorRepositoryImpl(final ScheduleColorJpaRepository scheduleColorJpaRepository) {
        this.scheduleColorJpaRepository = scheduleColorJpaRepository;
    }
}
