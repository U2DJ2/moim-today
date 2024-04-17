package moim_today.application.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableRequest;

import java.util.List;

public interface ScheduleService {

    List<Schedule> fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest);
}
