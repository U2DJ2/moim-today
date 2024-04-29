package moim_today.implement.schedule;

import moim_today.dto.schedule.ScheduleResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Implement
public class ScheduleFinder {

    private final ScheduleRepository scheduleRepository;

    public ScheduleFinder(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        // 1일 자정
        LocalDateTime startDateTime =
                yearMonth.atDay(1).atStartOfDay();

        // 다음달 1일 자정전
        LocalDateTime endDateTime =
                yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

        return scheduleRepository.findAllByMonthly(memberId, startDateTime, endDateTime);
    }
}
