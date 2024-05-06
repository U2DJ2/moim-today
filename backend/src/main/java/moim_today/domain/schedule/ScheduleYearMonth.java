package moim_today.domain.schedule;

import java.time.LocalDateTime;
import java.time.YearMonth;

public record ScheduleYearMonth(
        YearMonth yearMonth
) {

    public static ScheduleYearMonth from(final YearMonth yearMonth) {
        return new ScheduleYearMonth(yearMonth);
    }

    // 당월 1일 자정
    public LocalDateTime atMonthlyStartDateTime() {
        return yearMonth.atDay(1).atStartOfDay();
    }

    // 다음달 1일 자정전
    public LocalDateTime atMonthlyEndDateTime() {
        return yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);
    }
}
