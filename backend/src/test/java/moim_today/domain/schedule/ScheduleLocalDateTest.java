package moim_today.domain.schedule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleLocalDateTest {

    @DisplayName("날짜 정보를 입력받아 해당 날짜의 자정 시간으로 반환한다.")
    @Test
    void atWeeklyStartDateTime() {
        // given
        LocalDate localDate = LocalDate.of(2024, 3, 4);
        ScheduleLocalDate scheduleLocalDate = ScheduleLocalDate.from(localDate);

        // when
        LocalDateTime startDateTime = scheduleLocalDate.atWeeklyStartDateTime();

        // then
        assertThat(startDateTime).isEqualTo(
                LocalDateTime.of(2024, 3, 4, 0, 0, 0)
        );
    }

    @DisplayName("날짜 정보를 입력받아 해당 날짜의 7일 후의 자정전 시간으로 반환한다.")
    @Test
    void atWeeklyEndDateTime() {
        // given
        LocalDate localDate = LocalDate.of(2024, 3, 4);
        ScheduleLocalDate scheduleLocalDate = ScheduleLocalDate.from(localDate);

        // when
        LocalDateTime startDateTime = scheduleLocalDate.atWeeklyEndDateTime();

        // then
        assertThat(startDateTime).isEqualTo(
                LocalDateTime.of(2024, 3, 10, 23, 59, 59, 999999999)
        );
    }
}