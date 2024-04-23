package moim_today.application.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.implement.schedule.ScheduleManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleManager scheduleManager;

    public ScheduleServiceImpl(final ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        String timeTableXML = scheduleManager.fetchTimetable(timeTableRequest.everytimeId());
        List<Schedule> schedules = scheduleManager.processTimetable(timeTableXML, timeTableRequest);
        scheduleManager.addTimeTables(schedules, memberId);
    }
}