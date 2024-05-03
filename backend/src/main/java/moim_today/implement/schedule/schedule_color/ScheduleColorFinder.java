package moim_today.implement.schedule.schedule_color;

import moim_today.domain.schedule.enums.ColorHex;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.persistence.repository.schedule.schedule_color.ScheduleColorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static moim_today.global.constant.NumberConstant.*;

@Implement
public class ScheduleColorFinder {

    private final ScheduleColorRepository scheduleColorRepository;

    public ScheduleColorFinder(final ScheduleColorRepository scheduleColorRepository) {
        this.scheduleColorRepository = scheduleColorRepository;
    }

    @Transactional(readOnly = true)
    public String getColorHex(final long memberId) {
        Optional<ScheduleColorJpaEntity> optionalEntity = scheduleColorRepository.findByMemberId(memberId);

        if(optionalEntity.isPresent()) {
            ScheduleColorJpaEntity scheduleColor = optionalEntity.get();
            return scheduleColor.calculateColorHex();
        } else {
            ColorHex colorHex = ColorHex.getHexByCount(SCHEDULE_COLOR_START_COUNT.value());
            return colorHex.value();
        }
    }

    @Transactional(readOnly = true)
    public int getColorCount(final long memberId) {
        Optional<ScheduleColorJpaEntity> optionalEntity = scheduleColorRepository.findByMemberId(memberId);

        return optionalEntity.map(ScheduleColorJpaEntity::getColorCount)
                .orElseGet(SCHEDULE_COLOR_START_COUNT::value);
    }
}
