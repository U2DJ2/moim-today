package moim_today.domain.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record Meeting(
        long moimId,
        String agenda,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String place,
        MeetingCategory meetingCategory
) {

    public static long calculateDDay(final LocalDate currentDate, final LocalDate startDate) {
        return ChronoUnit.DAYS.between(currentDate, startDate);
    }
}
