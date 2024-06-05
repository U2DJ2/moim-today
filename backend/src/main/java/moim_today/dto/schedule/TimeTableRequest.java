package moim_today.dto.schedule;

import jakarta.validation.constraints.NotNull;
import moim_today.global.error.BadRequestException;

import java.time.LocalDate;

import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_INPUT_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.EVERY_TIME_END_DATE_NULL_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.EVERY_TIME_START_DATE_NULL_ERROR;

public record TimeTableRequest(
        String everytimeUrl,
        @NotNull(message = EVERY_TIME_START_DATE_NULL_ERROR) LocalDate startDate,
        @NotNull(message = EVERY_TIME_END_DATE_NULL_ERROR)LocalDate endDate
) {

    public TimeTableRequest {
        if(startDate.isAfter(endDate)) {
            throw new BadRequestException(TIME_INPUT_ERROR.value());
        }
    }
}
