package moim_today.application.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.implement.schedule.ScheduleAppender;
import moim_today.implement.schedule.ScheduleManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleManager scheduleManager;
    private final ScheduleAppender scheduleAppender;

    public ScheduleServiceImpl(final ScheduleManager scheduleManager,
                               final ScheduleAppender scheduleAppender) {
        this.scheduleManager = scheduleManager;
        this.scheduleAppender = scheduleAppender;
    }

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        String timeTableXML = scheduleManager.fetchTimetable(timeTableRequest.everytimeId());
        List<Schedule> schedules = scheduleManager.processTimetable(timeTableXML, timeTableRequest);
        scheduleAppender.saveTimeTables(schedules, memberId);
    }
}