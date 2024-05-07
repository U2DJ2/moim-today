package moim_today.presentation.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.schedule.*;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;


@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/weekly")
    public CollectionResponse<List<ScheduleResponse>> findAllByWeekly(@Login final MemberSession memberSession,
                                                                      @RequestParam final LocalDate startDate) {
        List<ScheduleResponse> scheduleResponses = scheduleService.findAllByWeekly(memberSession.id(), startDate);
        return CollectionResponse.of(scheduleResponses);
    }

    @GetMapping("/weekly/available-time/moims/{moimId}")
    public CollectionResponse<List<AvailableTimeInMoimResponse>> findAvailableTimeInMoim(
            @PathVariable final long moimId,
            @RequestParam final LocalDate startDate) {
        List<AvailableTimeInMoimResponse> availableTimeInMoimResponses =
                scheduleService.findWeeklyAvailableTimeInMoim(moimId, startDate);
        return CollectionResponse.of(availableTimeInMoimResponses);
    }

    @GetMapping("/weekly/available-time/members/{memberId}")
    public CollectionResponse<List<AvailableTimeForMemberResponse>> findAvailableTimeForMember(
            @PathVariable final long memberId,
            @RequestParam final LocalDate startDate) {
        List<AvailableTimeForMemberResponse> availableTimeInMoimResponses =
                scheduleService.findWeeklyAvailableTimeForMember(memberId, startDate);
        return CollectionResponse.of(availableTimeInMoimResponses);
    }

    @GetMapping("/monthly")
    public CollectionResponse<List<ScheduleResponse>> findAllByMonthly(@Login final MemberSession memberSession,
                                                                       @RequestParam final YearMonth yearMonth) {
        List<ScheduleResponse> scheduleResponses = scheduleService.findAllByMonthly(memberSession.id(), yearMonth);
        return CollectionResponse.of(scheduleResponses);
    }

    @PostMapping("/timetable")
    public void fetchTimeTable(@Login final MemberSession memberSession,
                               @RequestBody final TimeTableRequest timeTableRequest) {
        scheduleService.fetchTimeTable(memberSession.id(), timeTableRequest);
    }

    @PostMapping
    public void createSchedule(@Login final MemberSession memberSession,
                               @RequestBody final ScheduleCreateRequest scheduleCreateRequest) {
        scheduleService.createSchedule(memberSession.id(), scheduleCreateRequest);
    }

    @PatchMapping
    public void updateSchedule(@Login final MemberSession memberSession,
                               @RequestBody final ScheduleUpdateRequest scheduleUpdateRequest) {
        scheduleService.updateSchedule(memberSession.id(), scheduleUpdateRequest);
    }

    @DeleteMapping
    public void deleteSchedule(@Login final MemberSession memberSession,
                               @RequestBody final ScheduleDeleteRequest scheduleDeleteRequest) {
        scheduleService.deleteSchedule(memberSession.id(), scheduleDeleteRequest.scheduleId());
    }
}