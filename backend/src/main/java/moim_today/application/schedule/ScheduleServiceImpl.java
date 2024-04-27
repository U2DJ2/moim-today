package moim_today.application.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.ScheduleCreateRequest;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.implement.schedule.ScheduleAppender;
import moim_today.implement.schedule.ScheduleDeleter;
import moim_today.implement.schedule.ScheduleManager;
import moim_today.implement.schedule.ScheduleUpdater;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleManager scheduleManager;
    private final ScheduleAppender scheduleAppender;
    private final ScheduleUpdater scheduleUpdater;
    private final ScheduleDeleter scheduleDeleter;

    public ScheduleServiceImpl(final ScheduleManager scheduleManager, final ScheduleAppender scheduleAppender,
                               final ScheduleUpdater scheduleUpdater, final ScheduleDeleter scheduleDeleter) {
        this.scheduleManager = scheduleManager;
        this.scheduleAppender = scheduleAppender;
        this.scheduleUpdater = scheduleUpdater;
        this.scheduleDeleter = scheduleDeleter;
    }

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        String timeTableXML = scheduleManager.fetchTimetable(timeTableRequest.everytimeId());
        List<Schedule> schedules = scheduleManager.processTimetable(timeTableXML, timeTableRequest);
        scheduleAppender.batchUpdateSchedules(schedules, memberId);
    }

    @Override
    public void createSchedule(final long memberId, final ScheduleCreateRequest scheduleCreateRequest) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleCreateRequest.toEntity(memberId);
        scheduleAppender.createSchedule(scheduleJpaEntity);
    }

    @Override
    public void updateSchedule(final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        scheduleUpdater.updateSchedule(memberId, scheduleUpdateRequest);
    }

    @Override
    public void deleteSchedule(final long memberId, final long scheduleId) {
        scheduleDeleter.deleteSchedule(memberId, scheduleId);
    }
}