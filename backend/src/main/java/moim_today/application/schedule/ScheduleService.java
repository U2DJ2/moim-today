package moim_today.application.schedule;

import moim_today.dto.schedule.ScheduleCreateRequest;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableRequest;


public interface ScheduleService {

    void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest);

    void createSchedule(final long memberId, final ScheduleCreateRequest scheduleCreateRequest);

    void updateSchedule(final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest);

    void deleteSchedule(final long memberId, final long scheduleId);
}
