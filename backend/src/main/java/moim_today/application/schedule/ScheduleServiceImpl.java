package moim_today.application.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.implement.schedule.ScheduleManager;
import moim_today.implement.schedule.SchedulingTaskQueue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleManager scheduleManager;
    private final SchedulingTaskQueue schedulingTaskQueue;

    public ScheduleServiceImpl(final ScheduleManager scheduleManager, final SchedulingTaskQueue schedulingTaskQueue) {
        this.scheduleManager = scheduleManager;
        this.schedulingTaskQueue = schedulingTaskQueue;
    }

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        String timeTableXML = scheduleManager.fetchTimetable(timeTableRequest.everytimeId());
        List<Schedule> schedules = scheduleManager.processTimetable(timeTableXML, timeTableRequest);
        schedulingTaskQueue.addTimeTables(schedules, memberId);
    }
}