package moim_today.persistence.entity.schedule.schedule_color;

import moim_today.domain.schedule.enums.ColorHex;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleColorJpaEntityTest {

    @DisplayName("색상 카운트를 수정한다.")
    @Test
    void updateColorCount() {
        // given
        long memberId = 1L;
        int colorCount = 10;
        int newCount = 20;

        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();

        // when
        scheduleColorJpaEntity.updateColorCount(newCount);

        // then
        assertThat(scheduleColorJpaEntity.getColorCount()).isEqualTo(newCount);
    }

    @DisplayName("스케줄 색상 카운트 정보를 바탕으로 hex 색상 정보를 반환한다.")
    @Test
    void calculateColorHex() {
        // given
        long memberId = 1L;
        int colorCount = 10;

        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();

        // when
        String colorHex = scheduleColorJpaEntity.calculateColorHex();

        // then
        ColorHex[] values = ColorHex.values();
        assertThat(colorHex).isEqualTo(values[colorCount].value());
    }
}