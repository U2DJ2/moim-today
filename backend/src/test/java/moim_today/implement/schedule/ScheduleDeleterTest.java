package moim_today.implement.schedule;

import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_FORBIDDEN;
import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;

class ScheduleDeleterTest extends ImplementTest {

    @Autowired
    private ScheduleDeleter scheduleDeleter;

    @DisplayName("개인 일정을 삭제한다.")
    @Test
    void deleteSchedule() {
        // given
        long memberId = 1L;

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // when
        scheduleDeleter.deleteSchedule(memberId, scheduleJpaEntity.getId());

        // then
        assertThat(scheduleRepository.count()).isEqualTo(0);
    }

    @DisplayName("해당 회원의 스케줄이 아니면 삭제할 수 없다.")
    @Test
    void deleteScheduleForbidden() {
        // given
        long memberId = 1L;
        long anotherMemberId = 9999L;

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // expected
        assertThatCode(() ->  scheduleDeleter.deleteSchedule(anotherMemberId, scheduleJpaEntity.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(SCHEDULE_FORBIDDEN.message());
    }

    @DisplayName("스케줄이 존재하지 않으면 삭제할 수 없다.")
    @Test
    void deleteScheduleNotFound() {
        // expected
        assertThatCode(() ->  scheduleDeleter.deleteSchedule(1L, 9999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(SCHEDULE_NOT_FOUND.message());
    }
}