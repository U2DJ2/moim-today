package moim_today.implement.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;


class SchedulingTaskQueueTest {

    @DisplayName("스케줄링 큐에 회원 id와 스케줄 정보를 추가한다.")
    @Test
    void addTimeTables() {
        // given 1
        SchedulingTaskQueue schedulingTaskQueue = new SchedulingTaskQueue();

        // given 2
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

        // when
        List<Schedule> schedules = List.of(schedule1, schedule2, schedule3);
        schedulingTaskQueue.addTimeTables(schedules, memberId);

        // then
        TimeTableSchedulingTask timeTableSchedulingTask = schedulingTaskQueue.peek();
        assertThat(timeTableSchedulingTask.schedules().size()).isEqualTo(3);
        assertThat(timeTableSchedulingTask.memberId()).isEqualTo(memberId);
    }

    @DisplayName("스케줄링 큐에 여러 사용자가 동시에 정보를 추가해도 동시성 문제가 발생하지 않는다.")
    @Test
    void addTimeTablesConcurrently() throws InterruptedException {
        // given 1
        SchedulingTaskQueue schedulingTaskQueue = new SchedulingTaskQueue();

        // given 2
        Schedule schedule1 = Schedule.builder()
                .scheduleName("스케줄 1")
                .build();

        Schedule schedule2 = Schedule.builder()
                .scheduleName("스케줄 2")
                .build();

        Schedule schedule3 = Schedule.builder()
                .scheduleName("스케줄 3")
                .build();

        // given 3
        int numberOfThreads = 10;
        int numberOfTasksPerThread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < numberOfTasksPerThread; j++) {
                    List<Schedule> schedules = List.of(schedule1, schedule2, schedule3);
                    long memberId = Thread.currentThread().getId();
                    schedulingTaskQueue.addTimeTables(schedules, memberId);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertThat(schedulingTaskQueue.size()).isEqualTo(numberOfThreads * numberOfTasksPerThread);
    }
}