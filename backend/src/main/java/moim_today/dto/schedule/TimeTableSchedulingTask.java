package moim_today.dto.schedule;

import moim_today.domain.schedule.Schedule;

import java.util.List;

public record TimeTableSchedulingTask(
        List<Schedule> schedules,
        long memberId
) {

    public static TimeTableSchedulingTask of(final List<Schedule> schedules, final long memberId) {
        return new TimeTableSchedulingTask(schedules, memberId);
    }
}
