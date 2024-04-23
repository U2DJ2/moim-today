package moim_today.implement.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.util.ImplementTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ScheduleAppenderTest extends ImplementTest {

    @Autowired
    private ScheduleAppender scheduleAppender;

    @DisplayName("시간표 정보로 스케줄 정보를 저장한다.")
    @Test
    void batchUpdateSchedules() {
        // given 1
        Schedule schedule1 = Schedule.builder()
                .scheduleName("스케줄 1")
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        Schedule schedule2 = Schedule.builder()
                .scheduleName("스케줄 2")
                .dayOfWeek(DayOfWeek.THURSDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        Schedule schedule3 = Schedule.builder()
                .scheduleName("스케줄 3")
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        long memberId = 1L;

        // given 2
        List<Schedule> schedules = List.of(schedule1, schedule2, schedule3);
        TimeTableSchedulingTask timeTableSchedulingTask = new TimeTableSchedulingTask(schedules, memberId);

        // when
        scheduleAppender.batchUpdateSchedules(timeTableSchedulingTask);

        // then
        assertThat(scheduleRepository.count()).isEqualTo(3);
    }
}