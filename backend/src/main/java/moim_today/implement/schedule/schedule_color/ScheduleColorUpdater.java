package moim_today.implement.schedule.schedule_color;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.persistence.repository.schedule.schedule_color.ScheduleColorRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class ScheduleColorUpdater {

    public final ScheduleColorRepository scheduleColorRepository;

    public ScheduleColorUpdater(final ScheduleColorRepository scheduleColorRepository) {
        this.scheduleColorRepository = scheduleColorRepository;
    }

    @Transactional
    public int updateColorCount(final long memberId, final int count) {
        ScheduleColorJpaEntity scheduleColorJpaEntity = scheduleColorRepository.getByMemberId(memberId);
        int currentCount = scheduleColorJpaEntity.getColorCount();
        int newCount = currentCount + count;
        scheduleColorJpaEntity.updateColorCount(newCount);

        return newCount;
    }
}