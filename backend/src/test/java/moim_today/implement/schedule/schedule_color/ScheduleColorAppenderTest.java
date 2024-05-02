package moim_today.implement.schedule.schedule_color;

import moim_today.persistence.entity.schedule.schedule_color.ScheduleColorJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class ScheduleColorAppenderTest extends ImplementTest {

    @Autowired
    private ScheduleColorAppender scheduleColorAppender;

    @DisplayName("스케줄 색상 정보를 저장한다.")
    @Test
    void save() {
        // given
        long memberId = 1;
        int colorCount = 10;

        // when
        scheduleColorAppender.save(memberId, colorCount);

        // then
        ScheduleColorJpaEntity findEntity = scheduleColorRepository.getByMemberId(memberId);

        assertThat(findEntity.getColorCount()).isEqualTo(colorCount);
        assertThat(scheduleColorRepository.count()).isEqualTo(1);
    }
}