package moim_today.application.schedule;

import moim_today.dto.schedule.TimeTableRequest;


public interface ScheduleService {

    void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest);
}
