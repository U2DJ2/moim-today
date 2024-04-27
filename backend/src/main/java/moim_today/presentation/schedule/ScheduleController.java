package moim_today.presentation.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.schedule.ScheduleCreateRequest;
import moim_today.dto.schedule.ScheduleDeleteRequest;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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