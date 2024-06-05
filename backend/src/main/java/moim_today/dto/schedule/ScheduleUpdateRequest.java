package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record ScheduleUpdateRequest(
        @Min(value = 1, message = SCHEDULER_ID_MIN_ERROR) long scheduleId,
        @NotBlank(message = SCHEDULE_NAME_BLANK_ERROR) String scheduleName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = SCHEDULE_START_DATE_TIME_NULL_ERROR) LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = SCHEDULE_END_DATE_TIME_NULL_ERROR) LocalDateTime endDateTime
) {
        private static final String SCHEDULER_ID_MIN_ERROR = "잘못된 스케줄 ID 값이 입력 되었습니다.";
        private static final String SCHEDULE_NAME_BLANK_ERROR = "스케줄 이름은 공백일 수 없습니다.";
        private static final String SCHEDULE_START_DATE_TIME_NULL_ERROR = "스케줄 시작 일자는 필수 입력 항목입니다.";
        private static final String SCHEDULE_END_DATE_TIME_NULL_ERROR = "스케줄 종료 일자는 필수 입력 항목입니다.";

}
