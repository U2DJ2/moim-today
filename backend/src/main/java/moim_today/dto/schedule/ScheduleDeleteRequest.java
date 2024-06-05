package moim_today.dto.schedule;

import jakarta.validation.constraints.Min;

public record ScheduleDeleteRequest(
        @Min(value = 0, message = SCHEDULER_ID_MIN_ERROR) long scheduleId
) {
    private static final String SCHEDULER_ID_MIN_ERROR = "잘못된 스케줄 ID 값이 입력 되었습니다.";
}
