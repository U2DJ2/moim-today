package moim_today.implement.schedule.schedule_color;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.persistence.repository.schedule.schedule_color.ScheduleColorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Implement
public class ScheduleColorManager {

    private final ScheduleColorRepository scheduleColorRepository;
    private final ScheduleColorAppender scheduleColorAppender;
    private final ScheduleColorFinder scheduleColorFinder;
    private final ScheduleColorUpdater scheduleColorUpdater;

    public ScheduleColorManager(final ScheduleColorRepository scheduleColorRepository,
                                final ScheduleColorAppender scheduleColorAppender,
                                final ScheduleColorFinder scheduleColorFinder,
                                final ScheduleColorUpdater scheduleColorUpdater) {
        this.scheduleColorRepository = scheduleColorRepository;
        this.scheduleColorAppender = scheduleColorAppender;
        this.scheduleColorFinder = scheduleColorFinder;
        this.scheduleColorUpdater = scheduleColorUpdater;
    }

    public String getColorHex(final long memberId) {
        return scheduleColorFinder.getColorHex(memberId);
    }

    public int getColorCount(final long memberId) {
        return scheduleColorFinder.getColorCount(memberId);
    }

    @Transactional
    public void updateColorCount(final long memberId, final int nextCount) {
        Optional<ScheduleColorJpaEntity> optionalEntity = scheduleColorRepository.findByMemberId(memberId);

        if (optionalEntity.isEmpty()) {
            scheduleColorAppender.save(memberId, nextCount);
        } else {
            scheduleColorUpdater.updateColorCount(memberId, nextCount);
        }
    }
}
