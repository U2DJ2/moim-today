package moim_today.persistence.repository.schedule;

import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;


public interface ScheduleRepository {

    ScheduleJpaEntity getById(final long scheduleId);

    void save(final ScheduleJpaEntity scheduleJpaEntity);

    void batchUpdate(final TimeTableSchedulingTask timeTableSchedulingTask);

    boolean exists(final ScheduleJpaEntity scheduleJpaEntity);

    boolean existsExcludeEntity(final long scheduleId, final ScheduleUpdateRequest scheduleUpdateRequest);

    long count();
}
