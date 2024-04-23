package moim_today.implement.schedule;

import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;


@Implement
public class ScheduleAppender {

    private final ScheduleRepository scheduleRepository;

    public ScheduleAppender(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void batchUpdateSchedules(final TimeTableSchedulingTask timeTableSchedulingTask) {
        scheduleRepository.batchUpdate(timeTableSchedulingTask);
    }
}
