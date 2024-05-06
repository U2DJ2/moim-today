package moim_today.domain.schedule;


import java.time.LocalDate;
import java.time.LocalDateTime;

import static moim_today.global.constant.TimeConstant.SIX_DAY;

public record ScheduleLocalDate(
        LocalDate startDate
) {

    public static ScheduleLocalDate from(final LocalDate startDate) {
        return new ScheduleLocalDate(startDate);
    }

    // 시작 날짜의 자정
    public LocalDateTime atWeeklyStartDateTime() {
        return startDate.atStartOfDay();
    }

    // 시작 날짜로부터 7일 후의 자정 전
    public LocalDateTime atWeeklyEndDateTime() {
        return startDate.plusDays(SIX_DAY.time())
                .atTime(23, 59, 59, 999999999);
    }
}
