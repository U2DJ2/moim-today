package moim_today.domain.schedule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.*;

class ScheduleYearMonthTest {

    @DisplayName("당월 1일 자정 시간을 반환한다.")
    @Test
    void atMonthlyStartDateTime() {
        // given
        YearMonth yearMonth = YearMonth.of(2024, 3);
        ScheduleYearMonth scheduleYearMonth = ScheduleYearMonth.from(yearMonth);

        // when
        LocalDateTime startDateTime = scheduleYearMonth.atMonthlyStartDateTime();

        // then
        assertThat(startDateTime).isEqualTo(
                LocalDateTime.of(2024, 3, 1, 0, 0, 0)
        );
    }

    @DisplayName("다음달 1일 자정전 시간을 반환한다.")
    @Test
    void atMonthlyEndDateTime() {
        // given
        YearMonth yearMonth = YearMonth.of(2024, 3);
        ScheduleYearMonth scheduleYearMonth = ScheduleYearMonth.from(yearMonth);

        // when
        LocalDateTime startDateTime = scheduleYearMonth.atMonthlyEndDateTime();

        // then
        assertThat(startDateTime).isEqualTo(
                LocalDateTime.of(2024, 3, 31, 23, 59, 59, 999999999)
        );
    }
}