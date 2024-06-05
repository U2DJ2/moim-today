package moim_today.implement.schedule.schedule_color;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.persistence.repository.schedule.schedule_color.ScheduleColorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Implement
public class ScheduleColorUpdater {

    private final ScheduleColorRepository scheduleColorRepository;
    private final ScheduleColorAppender scheduleColorAppender;

    public ScheduleColorUpdater(final ScheduleColorRepository scheduleColorRepository,
                                final ScheduleColorAppender scheduleColorAppender) {
        this.scheduleColorRepository = scheduleColorRepository;
        this.scheduleColorAppender = scheduleColorAppender;
    }

    @Transactional
    public void updateColorCount(final long memberId, final int nextCount) {
        Optional<ScheduleColorJpaEntity> optionalEntity = scheduleColorRepository.findByMemberId(memberId);

        if (optionalEntity.isEmpty()) {
            scheduleColorAppender.save(memberId, nextCount);
        } else {
            updateColor(memberId, nextCount);
        }
    }

    private void updateColor(final long memberId, final int count) {
        ScheduleColorJpaEntity scheduleColorJpaEntity = scheduleColorRepository.getByMemberId(memberId);
        int currentCount = scheduleColorJpaEntity.getColorCount();
        int newCount = currentCount + count;
        scheduleColorJpaEntity.updateColorCount(newCount);
    }
}