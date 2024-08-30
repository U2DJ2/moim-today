package moim_today.domain.schedule;

import moim_today.domain.member.Member;
import moim_today.dto.schedule.MoimScheduleResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static moim_today.global.constant.NumberConstant.AVAILABLE_TIME_OFFSET;
import static moim_today.global.constant.NumberConstant.SCHEDULE_TIME_START_IDX;


public record AvailableTime(
        List<Member> members,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    public static AvailableTime toDomain(final List<Member> members, final LocalDateTime startDateTime,
                                         final LocalDateTime endDateTime) {
        return new AvailableTime(members, startDateTime, endDateTime);
    }

    public static List<AvailableTime> calculateAvailableTimes(final List<MoimScheduleResponse> moimScheduleResponses,
                                                              final LocalDate startDate) {
        ScheduleLocalDate scheduleLocalDate = ScheduleLocalDate.from(startDate);
        List<LocalDateTime> allTimes = getAllTimes(moimScheduleResponses, scheduleLocalDate);
        return getAvailableTimes(allTimes, moimScheduleResponses);
    }

    private static List<LocalDateTime> getAllTimes(final List<MoimScheduleResponse> moimScheduleResponses,
                                                   final ScheduleLocalDate scheduleLocalDate) {
        List<LocalDateTime> times = getTimesFromSchedules(moimScheduleResponses);
        List<LocalDateTime> allTimes = new ArrayList<>(times);
        allTimes.add(SCHEDULE_TIME_START_IDX.value(), scheduleLocalDate.atWeeklyStartDateTime());
        allTimes.add(scheduleLocalDate.atWeeklyEndDateTime());

        return allTimes;
    }

    private static List<LocalDateTime> getTimesFromSchedules(final List<MoimScheduleResponse> moimScheduleResponses) {
        return moimScheduleResponses.stream()
                .flatMap(schedule -> Stream.of(schedule.startDateTime(), schedule.endDateTime()))
                .distinct()
                .sorted()
                .toList();
    }

    private static List<AvailableTime> getAvailableTimes(final List<LocalDateTime> allTimes,
                                                         final List<MoimScheduleResponse> moimScheduleResponses) {
        List<AvailableTime> availableTimes = new ArrayList<>();

        for (int i = 0; i < allTimes.size() - AVAILABLE_TIME_OFFSET.value(); i++) {
            LocalDateTime startDateTime = allTimes.get(i);
            LocalDateTime endDateTime = allTimes.get(i + AVAILABLE_TIME_OFFSET.value());
            List<Member> availableMembers = Member.filterByDateTime(moimScheduleResponses, startDateTime, endDateTime);

            if (!availableMembers.isEmpty()) {
                availableTimes.add(AvailableTime.toDomain(availableMembers, startDateTime, endDateTime));
            }
        }

        return availableTimes;
    }
}
