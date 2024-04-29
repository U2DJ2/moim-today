package moim_today.presentation.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.schedule.*;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;


@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
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