package moim_today.fake_class.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.domain.schedule.enums.ColorHex;
import moim_today.dto.schedule.ScheduleCreateRequest;
import moim_today.dto.schedule.ScheduleResponse;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;

import java.time.*;
import java.util.List;

import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_INPUT_ERROR;
import static moim_today.global.constant.exception.ScheduleExceptionConstant.*;
import static moim_today.util.TestConstant.*;

public class FakeScheduleService implements ScheduleService {

    @Override
    public List<ScheduleResponse> findAllByWeekly(final long memberId, final LocalDate localDate) {
        ScheduleResponse scheduleResponse1 = ScheduleResponse.builder()
                .scheduleId(1L)
                .meetingId(0L)
                .scheduleName("스케줄명 1")
                .dayOfWeek(DayOfWeek.MONDAY)
                .colorHex(ColorHex.getHexByCount(0).value())
                .startDateTime(LocalDateTime.of(2024, 03, 04, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 03, 04, 12, 0, 0))
                .build();

        ScheduleResponse scheduleResponse2 = ScheduleResponse.builder()
                .scheduleId(2L)
                .meetingId(0L)
                .scheduleName("스케줄명 2")
                .dayOfWeek(DayOfWeek.TUESDAY)
                .colorHex(ColorHex.getHexByCount(1).value())
                .startDateTime(LocalDateTime.of(2024, 03, 05, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 03, 05, 13, 15, 0))
                .build();

        ScheduleResponse scheduleResponse3 = ScheduleResponse.builder()
                .scheduleId(3L)
                .meetingId(1L)
                .scheduleName("스케줄명 3")
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .colorHex(ColorHex.getHexByCount(3).value())
                .startDateTime(LocalDateTime.of(2024, 03, 06, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 03, 06, 13, 15, 0))
                .build();

        return List.of(scheduleResponse1, scheduleResponse2, scheduleResponse3);
    }

    @Override
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        ScheduleResponse scheduleResponse1 = ScheduleResponse.builder()
                .scheduleId(1L)
                .meetingId(0L)
                .scheduleName("스케줄명 1")
                .dayOfWeek(DayOfWeek.MONDAY)
                .colorHex(ColorHex.getHexByCount(0).value())
                .startDateTime(LocalDateTime.of(2024, 03, 04, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 03, 04, 12, 0, 0))
                .build();

        ScheduleResponse scheduleResponse2 = ScheduleResponse.builder()
                .scheduleId(2L)
                .meetingId(0L)
                .scheduleName("스케줄명 2")
                .dayOfWeek(DayOfWeek.TUESDAY)
                .colorHex(ColorHex.getHexByCount(1).value())
                .startDateTime(LocalDateTime.of(2024, 03, 05, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 03, 05, 13, 15, 0))
                .build();

        ScheduleResponse scheduleResponse3 = ScheduleResponse.builder()
                .scheduleId(3L)
                .meetingId(1L)
                .scheduleName("스케줄명 3")
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .colorHex(ColorHex.getHexByCount(2).value())
                .startDateTime(LocalDateTime.of(2024, 03, 06, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 03, 06, 13, 15, 0))
                .build();

        return List.of(scheduleResponse1, scheduleResponse2, scheduleResponse3);
    }

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        if (!timeTableRequest.everytimeUrl().equals(EVERY_TIME_ID.value())) {
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
