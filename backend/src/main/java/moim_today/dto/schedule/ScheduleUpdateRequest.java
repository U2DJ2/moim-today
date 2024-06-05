package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;


@Builder
public record ScheduleUpdateRequest(
        @Min(value = 0, message = SCHEDULER_ID_MIN_ERROR) long scheduleId,
        @NotBlank(message = SCHEDULE_NAME_BLANK_ERROR) String scheduleName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = SCHEDULE_START_DATE_TIME_NULL_ERROR) LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = SCHEDULE_END_DATE_TIME_NULL_ERROR) LocalDateTime endDateTime
) {

}
