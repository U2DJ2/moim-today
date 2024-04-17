package moim_today.implement.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.NumberConstant.*;

@Implement
public class ScheduleAppender {

    private final ScheduleRepository scheduleRepository;

    public ScheduleAppender(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void saveTimeTables(final List<Schedule> schedules, final long memberId) {
        schedules.stream()
                .map(schedule -> schedule.toEntity(memberId, SCHEDULE_MEETING_ID.value()))
                .forEach(scheduleRepository::save);
    }
}
