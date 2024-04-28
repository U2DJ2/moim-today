package moim_today.implement.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import moim_today.persistence.repository.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_ALREADY_EXIST;


@Implement
public class ScheduleAppender {

    private final ScheduleRepository scheduleRepository;

    public ScheduleAppender(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void batchUpdateSchedules(final List<Schedule> schedules, final long memberId) {
        TimeTableSchedulingTask timeTableSchedulingTask = TimeTableSchedulingTask.of(schedules, memberId);
        scheduleRepository.batchUpdate(timeTableSchedulingTask);
    }

    @Transactional
    public void createSchedule(final ScheduleJpaEntity scheduleJpaEntity) {
        validateAlreadyExist(scheduleJpaEntity);
        scheduleRepository.save(scheduleJpaEntity);
    }

    private void validateAlreadyExist(final ScheduleJpaEntity scheduleJpaEntity) {
        if (scheduleRepository.exists(scheduleJpaEntity)) {
            throw new BadRequestException(SCHEDULE_ALREADY_EXIST.message());
        }
    }
}