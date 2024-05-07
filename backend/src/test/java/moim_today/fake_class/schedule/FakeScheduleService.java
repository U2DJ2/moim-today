package moim_today.fake_class.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.domain.schedule.enums.AvailableColorHex;
import moim_today.domain.schedule.enums.ColorHex;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.dto.schedule.*;
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
    public List<AvailableTimeInMoimResponse> findWeeklyAvailableTimeInMoim(
            final long moimId, final LocalDate startDate) {
        MemberSimpleResponse memberSimpleResponse1 = MemberSimpleResponse.builder()
                .memberId(1)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        MemberSimpleResponse memberSimpleResponse2 = MemberSimpleResponse.builder()
                .memberId(2)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        MemberSimpleResponse memberSimpleResponse3 = MemberSimpleResponse.builder()
                .memberId(3)
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        List<MemberSimpleResponse> memberSimpleResponses1 = List.of(memberSimpleResponse1, memberSimpleResponse2);
        List<MemberSimpleResponse> memberSimpleResponses2 = List.of(memberSimpleResponse1, memberSimpleResponse2, memberSimpleResponse3);
        List<MemberSimpleResponse> memberSimpleResponses3 = List.of(memberSimpleResponse2);

        AvailableTimeInMoimResponse availableTimeInMoimResponse1 = AvailableTimeInMoimResponse.builder()
                .members(memberSimpleResponses1)
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .colorHex(AvailableColorHex.getHexByCount(2))
                .build();

        AvailableTimeInMoimResponse availableTimeInMoimResponse2 = AvailableTimeInMoimResponse.builder()
                .members(memberSimpleResponses2)
                .startDateTime(LocalDateTime.of(2024, 3, 4, 14, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 16, 0, 0))
                .colorHex(AvailableColorHex.getHexByCount(3))
                .build();

        AvailableTimeInMoimResponse availableTimeInMoimResponse3 = AvailableTimeInMoimResponse.builder()
                .members(memberSimpleResponses3)
                .startDateTime(LocalDateTime.of(2024, 3, 4, 18, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 20, 0, 0))
                .colorHex(AvailableColorHex.getHexByCount(1))
                .build();

        return List.of(availableTimeInMoimResponse1, availableTimeInMoimResponse2, availableTimeInMoimResponse3);
    }

    @Override
    public List<AvailableTimeForMemberResponse> findWeeklyAvailableTimeForMember(
            final long memberId, final LocalDate startDate) {

        AvailableTimeForMemberResponse availableTimeForMemberResponse1 = AvailableTimeForMemberResponse.builder()
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .colorHex(AvailableColorHex.getHexByCount(0))
                .build();

        AvailableTimeForMemberResponse availableTimeForMemberResponse2 = AvailableTimeForMemberResponse.builder()
                .startDateTime(LocalDateTime.of(2024, 3, 4, 14, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 16, 0, 0))
                .colorHex(AvailableColorHex.getHexByCount(0))
                .build();

        AvailableTimeForMemberResponse availableTimeForMemberResponse3 = AvailableTimeForMemberResponse.builder()
                .startDateTime(LocalDateTime.of(2024, 3, 4, 18, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 20, 0, 0))
                .colorHex(AvailableColorHex.getHexByCount(1))
                .build();

        return List.of(
                availableTimeForMemberResponse1,
                availableTimeForMemberResponse2,
                availableTimeForMemberResponse3
        );
    }

    @Override
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        ScheduleResponse scheduleResponse1 = ScheduleResponse.builder()
                .scheduleId(1L)
                .meetingId(0L)
                .scheduleName("스케줄명 1")
                .dayOfWeek(DayOfWeek.MONDAY)
                .colorHex(ColorHex.getHexByCount(0).value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        ScheduleResponse scheduleResponse2 = ScheduleResponse.builder()
                .scheduleId(2L)
                .meetingId(0L)
                .scheduleName("스케줄명 2")
                .dayOfWeek(DayOfWeek.TUESDAY)
                .colorHex(ColorHex.getHexByCount(1).value())
                .startDateTime(LocalDateTime.of(2024, 3, 5, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 5, 13, 15, 0))
                .build();

        ScheduleResponse scheduleResponse3 = ScheduleResponse.builder()
                .scheduleId(3L)
                .meetingId(1L)
                .scheduleName("스케줄명 3")
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .colorHex(ColorHex.getHexByCount(2).value())
                .startDateTime(LocalDateTime.of(2024, 3, 6, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 6, 13, 15, 0))
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
