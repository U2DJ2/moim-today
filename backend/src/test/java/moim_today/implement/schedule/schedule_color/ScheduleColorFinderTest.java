package moim_today.implement.schedule.schedule_color;

import moim_today.domain.schedule.enums.ColorHex;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class ScheduleColorFinderTest extends ImplementTest {

    @Autowired
    private ScheduleColorFinder scheduleColorFinder;

    @DisplayName("회원의 스케줄 색상 카운트 정보를 바탕으로 hex 색상 정보를 반환한다.")
    @Test
    void getColorHex() {
        // given
        long memberId = 1L;
        int colorCount = 10;

        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();

        scheduleColorRepository.save(scheduleColorJpaEntity);

        // when
        String colorHex = scheduleColorFinder.getColorHex(memberId);

        // then
        ColorHex[] values = ColorHex.values();
        assertThat(colorHex).isEqualTo(values[colorCount].value());
    }

    @DisplayName("회원의 스케줄 색상 카운트 정보가 없으면 첫번째 색상을 반환한다.")
    @Test
    void getFirstColorHex() {
        // given
        long memberId = 1L;

        // when
        String colorHex = scheduleColorFinder.getColorHex(memberId);

        // then
        ColorHex[] values = ColorHex.values();
        assertThat(colorHex).isEqualTo(values[0].value());
    }

    @DisplayName("회원의 스케줄 색상 카운트 정보를 반환한다.")
    @Test
    void getColorCount() {
        // given
        long memberId = 1L;
        int colorCount = 10;

        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();

        scheduleColorRepository.save(scheduleColorJpaEntity);

        // when
        int findColorCount = scheduleColorFinder.getColorCount(memberId);

        // then
        assertThat(findColorCount).isEqualTo(colorCount);
    }

    @DisplayName("회원의 스케줄 색상 카운트 정보가 존재하지 않으면 0을 반환한다.")
    @Test
    void getFirstColorCount() {
        // when
        long memberId = 9999L;

        int findColorCount = scheduleColorFinder.getColorCount(memberId);

        // then
        assertThat(findColorCount).isEqualTo(0);
    }
}