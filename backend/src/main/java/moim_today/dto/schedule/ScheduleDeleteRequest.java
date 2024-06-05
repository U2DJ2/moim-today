package moim_today.dto.schedule;

import jakarta.validation.constraints.Min;

import static moim_today.global.constant.exception.ValidationExceptionConstant.SCHEDULER_ID_MIN_ERROR;

public record ScheduleDeleteRequest(
        @Min(value = 0, message = SCHEDULER_ID_MIN_ERROR) long scheduleId
) {

}
