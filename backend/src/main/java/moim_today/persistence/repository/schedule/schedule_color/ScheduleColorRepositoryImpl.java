package moim_today.persistence.repository.schedule.schedule_color;

import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class ScheduleColorRepositoryImpl implements ScheduleColorRepository {

    private final ScheduleColorJpaRepository scheduleColorJpaRepository;

    public ScheduleColorRepositoryImpl(final ScheduleColorJpaRepository scheduleColorJpaRepository) {
        this.scheduleColorJpaRepository = scheduleColorJpaRepository;
    }

    @Override
    public Optional<ScheduleColorJpaEntity> findByMemberId(final long memberId) {
        return scheduleColorJpaRepository.findByMemberId(memberId);
    }

    @Override
    public void save(final ScheduleColorJpaEntity scheduleColorJpaEntity) {
        scheduleColorJpaRepository.save(scheduleColorJpaEntity);
    }
}
