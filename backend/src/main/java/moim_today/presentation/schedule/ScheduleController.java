package moim_today.presentation.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/timetable")
    public List<Schedule> getTimeTable(@RequestBody final TimeTableRequest timeTableRequest) {
        return scheduleService.fetchTimeTable(1L, timeTableRequest);
    }
}