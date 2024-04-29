package moim_today.fake_class.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.dto.schedule.ScheduleCreateRequest;
import moim_today.dto.schedule.ScheduleResponse;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_INPUT_ERROR;
import static moim_today.global.constant.exception.ScheduleExceptionConstant.*;
import static moim_today.util.TestConstant.*;

public class FakeScheduleService implements ScheduleService {

    @Override
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        return List.of();
    }

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

    @Override
    public void updateSchedule(final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        if (scheduleUpdateRequest.scheduleId() == Long.parseLong(FORBIDDEN_SCHEDULE_ID.value())) {
            throw new ForbiddenException(SCHEDULE_FORBIDDEN.message());
        }

        if(scheduleUpdateRequest.startDateTime().equals(LocalDateTime.of(2024, 1, 1, 0, 0, 0))) {
            throw new BadRequestException(SCHEDULE_ALREADY_EXIST.message());
        }
    }

    @Override
    public void deleteSchedule(final long memberId, final long scheduleId) {
        if (scheduleId == Long.parseLong(NOTFOUND_SCHEDULE_ID.value())) {
            throw new NotFoundException(SCHEDULE_NOT_FOUND.message());
        }

        if (scheduleId == Long.parseLong(FORBIDDEN_SCHEDULE_ID.value())) {
            throw new ForbiddenException(SCHEDULE_FORBIDDEN.message());
        }
    }
}
