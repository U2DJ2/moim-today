package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
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

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime
) {

    @QueryProjection
    public ScheduleResponse {
    }
}
