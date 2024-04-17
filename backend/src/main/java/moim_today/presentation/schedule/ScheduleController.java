package moim_today.presentation.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/timetable")
    public void getTimeTable(@Login final MemberSession memberSession,
                             @RequestBody final TimeTableRequest timeTableRequest) {
        scheduleService.fetchTimeTable(memberSession.id(), timeTableRequest);
    }
}