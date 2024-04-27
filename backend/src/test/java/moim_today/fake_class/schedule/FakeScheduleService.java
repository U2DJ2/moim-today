package moim_today.fake_class.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.dto.schedule.ScheduleCreateRequest;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.error.BadRequestException;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_INPUT_ERROR;
import static moim_today.global.constant.exception.ScheduleExceptionConstant.*;
import static moim_today.util.TestConstant.*;

public class FakeScheduleService implements ScheduleService {

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        if (!timeTableRequest.everytimeId().equals(EVERY_TIME_ID.value())) {
            throw new BadRequestException(TIME_INPUT_ERROR.value());
        }
    }

    @Override
    public void createSchedule(final long memberId, final ScheduleCreateRequest scheduleCreateRequest) {
        if (scheduleCreateRequest.startDateTime().equals(LocalDateTime.of(2024, 1, 1, 0, 0, 0))) {
            throw new BadRequestException(SCHEDULE_ALREADY_EXIST.message());
        }
    }
}
