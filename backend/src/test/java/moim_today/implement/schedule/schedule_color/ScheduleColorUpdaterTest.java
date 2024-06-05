package moim_today.implement.schedule.schedule_color;

import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;


class ScheduleColorUpdaterTest extends ImplementTest {

    @Autowired
    private ScheduleColorUpdater scheduleColorUpdater;

    @DisplayName("회원의 스케줄 색상 정보를 수정한다.")
    @Test
    void updateColorCount() {
        // given
        long memberId = 1L;
        int colorCount = 1;
        int nextCount = 10;

        ScheduleColorJpaEntity scheduleColorJpaEntity = ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();

        scheduleColorRepository.save(scheduleColorJpaEntity);

        // when
        scheduleColorUpdater.updateColorCount(memberId, nextCount);

        // then
        ScheduleColorJpaEntity findEntity = scheduleColorRepository.getByMemberId(memberId);
        assertThat(findEntity.getColorCount()).isEqualTo(colorCount + nextCount);
    }
}