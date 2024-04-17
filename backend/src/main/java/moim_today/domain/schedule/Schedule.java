package moim_today.domain.schedule;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
public record Schedule(
        long memberId,
        long meetingId,
        String scheduleName,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    public static Schedule toDomain(final long memberId, final long meetingId, final String scheduleName,
                                    final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return Schedule.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(scheduleName)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }

    private LocalDate adjustStartDate(final LocalDate startDate, final DayOfWeek startDayOfWeek) {
        int daysToAdd = (startDayOfWeek.getValue() - startDate.getDayOfWeek().getValue() + 7) % 7;
        return startDate.plusDays(daysToAdd);
    }
}
