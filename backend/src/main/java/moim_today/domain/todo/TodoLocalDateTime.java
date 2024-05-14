package moim_today.domain.todo;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public record TodoLocalDateTime(
        LocalDateTime startDateIme
) {

    // 시작 날짜로부터 n개월 뒤의 달의 마지막 날짜 자정 전
    public java.time.LocalDateTime atMonthEndDateTime(final int months) {
        return startDateIme.plusMonths(months)
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
    }
}
