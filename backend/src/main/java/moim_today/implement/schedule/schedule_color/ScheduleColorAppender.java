package moim_today.implement.schedule.schedule_color;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.persistence.repository.schedule.schedule_color.ScheduleColorRepository;
import org.springframework.transaction.annotation.Transactional;


@Implement
public class ScheduleColorAppender {

    private final ScheduleColorRepository scheduleColorRepository;

    public ScheduleColorAppender(final ScheduleColorRepository scheduleColorRepository) {
        this.scheduleColorRepository = scheduleColorRepository;
    }

    @Transactional
    public void save(final long memberId) {
        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.toEntity(memberId);
        scheduleColorRepository.save(scheduleColorJpaEntity);
    }
}
