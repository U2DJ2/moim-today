package moim_today.dto.schedule;

import moim_today.global.error.BadRequestException;

import java.time.LocalDate;

import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_INPUT_ERROR;

public record TimeTableRequest(
        String everytimeUrl,
        LocalDate startDate,
        LocalDate endDate
) {

    public TimeTableRequest {
        if(startDate.isAfter(endDate)) {
            throw new BadRequestException(TIME_INPUT_ERROR.value());
        }
    }
}
