package moim_today.persistence.repository.schedule;

import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;

public interface ScheduleRepository {

    void save(final ScheduleJpaEntity scheduleJpaEntity);

    void batchUpdate(final TimeTableSchedulingTask timeTableSchedulingTask);
}
