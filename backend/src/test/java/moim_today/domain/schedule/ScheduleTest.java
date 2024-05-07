package moim_today.domain.schedule;

import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


class ScheduleTest {

    @DisplayName("새롭게 등록하는 스케줄 시작시간과 겹치는 스케줄이 있으면 true를 반환한다.")
    @Test
    void beforeScheduleAlreadyExist() {
        // given
        Schedule schedule = Schedule.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        ScheduleJpaEntity beforeScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 8, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 1))
                .build();

        // when
        boolean exist = schedule.exists(beforeScheduleJpaEntity);

        // then
        assertThat(exist).isTrue();
    }

    @DisplayName("새롭게 등록하는 스케줄 시작시간과 겹치지 않으면 false를 반환한다.")
    @Test
    void beforeScheduleNotExist() {
        // given
        Schedule schedule = Schedule.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        ScheduleJpaEntity beforeScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 8, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        // when
        boolean exist = schedule.exists(beforeScheduleJpaEntity);

        // then
        assertThat(exist).isFalse();
    }

    @DisplayName("새롭게 등록하는 스케줄 시작시간과 겹치는 스케줄이 있으면 true를 반환한다.")
    @Test
    void afterScheduleAlreadyExist() {
        // given
        Schedule schedule = Schedule.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        ScheduleJpaEntity afterScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 11, 59, 59))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        // when
        boolean exist = schedule.exists(afterScheduleJpaEntity);

        // then
        assertThat(exist).isTrue();
    }

    @DisplayName("새롭게 등록하는 스케줄 시작시간과 겹치지 않으면 false를 반환한다.")
    @Test
    void afterScheduleNotExist() {
        // given
        Schedule schedule = Schedule.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        ScheduleJpaEntity afterScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        // when
        boolean exist = schedule.exists(afterScheduleJpaEntity);

        // then
        assertThat(exist).isFalse();
    }
}