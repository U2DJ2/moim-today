package moim_today.implement.schedule;

import moim_today.dto.schedule.ScheduleResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.schedule.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.TimeConstant.SIX_DAY;

@Implement
public class ScheduleFinder {

    private final ScheduleRepository scheduleRepository;

    public ScheduleFinder(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAllByWeekly(final long memberId, final LocalDate startDate) {
        // 시작 날짜의 자정
        LocalDateTime startDateTime = startDate.atStartOfDay();

        // 시작 날짜로부터 7일 후의 자정 전
        LocalDateTime endDateTime
                = startDate.plusDays(SIX_DAY.time())
                .atTime(23, 59, 59, 999999999);

        return scheduleRepository.findAllByDateTime(memberId, startDateTime, endDateTime);
    }


    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAllByMonthly(final long memberId, final YearMonth yearMonth) {
        // 1일 자정
        LocalDateTime startDateTime =
                yearMonth.atDay(1).atStartOfDay();

        // 다음달 1일 자정전
        LocalDateTime endDateTime =
                yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

        return scheduleRepository.findAllByDateTime(memberId, startDateTime, endDateTime);
    }
}
