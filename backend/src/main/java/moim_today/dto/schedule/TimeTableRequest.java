package moim_today.dto.schedule;

import jakarta.validation.constraints.NotNull;
import moim_today.global.error.BadRequestException;

import java.time.LocalDate;

import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_INPUT_ERROR;

public record TimeTableRequest(
        String everytimeUrl,
        @NotNull(message = EVERY_TIME_START_DATE_NULL_ERROR) LocalDate startDate,
        @NotNull(message = EVERY_TIME_END_DATE_NULL_ERROR)LocalDate endDate
) {
    private static final String EVERY_TIME_START_DATE_NULL_ERROR = "시간표 추가의 시작 일자는 필수 입력 항목입니다.";
    private static final String EVERY_TIME_END_DATE_NULL_ERROR = "시간표 추가의 종료 일자는 필수 입력 항목입니다.";

    public TimeTableRequest {
        if(startDate.isAfter(endDate)) {
            throw new BadRequestException(TIME_INPUT_ERROR.value());
        }
    }
}
