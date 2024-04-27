package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static moim_today.global.constant.NumberConstant.SCHEDULE_MEETING_ID;

@Builder
public record ScheduleUpdateRequest(
        long scheduleId,
        String scheduleName,
        DayOfWeek dayOfWeek,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime
) {
}
