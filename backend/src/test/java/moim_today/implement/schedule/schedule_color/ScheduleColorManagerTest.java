package moim_today.implement.schedule.schedule_color;

import moim_today.domain.schedule.enums.ColorHex;
import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.util.ImplementTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ScheduleColorManagerTest extends ImplementTest {

    @Autowired
    private ScheduleColorManager scheduleColorManager;

    @DisplayName("회원의 스케줄 색상 카운트 정보를 바탕으로 hex 색상 정보를 반환한다.")
    @Test
    void getColorHex() {
        long memberId = 1L;
        int colorCount = 10;

        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();

        scheduleColorRepository.save(scheduleColorJpaEntity);

        // when
        String colorHex = scheduleColorManager.getColorHex(memberId);

        // then
        ColorHex[] values = ColorHex.values();
        assertThat(colorHex).isEqualTo(values[colorCount].value());
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
        int findColorCount = scheduleColorManager.getColorCount(memberId);

        // then
        assertThat(findColorCount).isEqualTo(colorCount);
    }

    @DisplayName("회원의 스케줄 색상 카운트 정보가 존재하지 않으면 새롭게 만든다.")
    @Test
    void updateColorCount() {
        // given
        long memberId = 1L;
        int nextCount = 5;

        // when
        scheduleColorManager.updateColorCount(memberId, nextCount);

        // then
        ScheduleColorJpaEntity findEntity = scheduleColorRepository.getByMemberId(memberId);
        assertThat(findEntity.getColorCount()).isEqualTo(nextCount);
    }

    @DisplayName("회원의 스케줄 색상 카운트 정보가 존재하면 더하여 수정한다.")
    @Test
    void updateColorCountAlreadyExist() {
        // given
        long memberId = 1L;
        int colorCount = 10;
        int nextCount = 5;

        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();

        scheduleColorRepository.save(scheduleColorJpaEntity);

        // when
        scheduleColorManager.updateColorCount(memberId, nextCount);

        // then
        ScheduleColorJpaEntity findEntity = scheduleColorRepository.getByMemberId(memberId);
        assertThat(findEntity.getColorCount()).isEqualTo(colorCount + nextCount);
    }

}