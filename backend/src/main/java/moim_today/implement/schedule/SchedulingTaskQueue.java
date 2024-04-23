package moim_today.implement.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.global.annotation.Implement;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


@Implement
public class SchedulingTaskQueue {

    private final Queue<TimeTableSchedulingTask> queue = new ConcurrentLinkedQueue<>();

    public void addTimeTables(final List<Schedule> schedules, final long memberId) {
        TimeTableSchedulingTask timeTableSchedulingTask = TimeTableSchedulingTask.of(schedules, memberId);
        queue.add(timeTableSchedulingTask);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public TimeTableSchedulingTask peek() {
        return queue.peek();
    }

    public void poll() {
        queue.poll();
    }

    public int size() {
        return queue.size();
    }
}
