package moim_today.implement.schedule.schedule_color;

import moim_today.domain.schedule.enums.ColorHex;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.persistence.repository.schedule.schedule_color.ScheduleColorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static moim_today.global.constant.NumberConstant.SCHEDULE_COLOR_START_COUNT;

@Implement
public class ScheduleColorManager {

    private final ScheduleColorRepository scheduleColorRepository;
    private final ScheduleColorAppender scheduleColorAppender;
    private final ScheduleColorUpdater scheduleColorUpdater;

    public ScheduleColorManager(final ScheduleColorRepository scheduleColorRepository,
                                final ScheduleColorAppender scheduleColorAppender,
                                final ScheduleColorUpdater scheduleColorUpdater) {
        this.scheduleColorRepository = scheduleColorRepository;
        this.scheduleColorAppender = scheduleColorAppender;
        this.scheduleColorUpdater = scheduleColorUpdater;
    }

    @Transactional
    public String getColorHex(final long memberId, final int nextCount) {
        Optional<ScheduleColorJpaEntity> optionalEntity = scheduleColorRepository.findByMemberId(memberId);
        int count = SCHEDULE_COLOR_START_COUNT.value();

        if (optionalEntity.isEmpty()) {
            scheduleColorAppender.save(memberId, count);
        } else {
            ScheduleColorJpaEntity scheduleColorJpaEntity = optionalEntity.get();
            count = scheduleColorUpdater.updateColorCount(scheduleColorJpaEntity, nextCount);
        }

        ColorHex colorHex = ColorHex.getHexByCount(count);
        return colorHex.value();
    }

    @Transactional
    public void updateColorCount(final long memberId, final int nextCount) {
        Optional<ScheduleColorJpaEntity> optionalEntity = scheduleColorRepository.findByMemberId(memberId);

        if (optionalEntity.isEmpty()) {
            scheduleColorAppender.save(memberId, nextCount);
        } else {
            ScheduleColorJpaEntity scheduleColorJpaEntity = optionalEntity.get();
            scheduleColorUpdater.updateColorCount(scheduleColorJpaEntity, nextCount);
        }
    }
}
