package moim_today.dto.schedule;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Builder
public record ScheduleResponse(
        long scheduleId,
        long meetingId,
        String scheduleName,
        DayOfWeek dayOfWeek,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    @QueryProjection
    public ScheduleResponse {
    }
}
