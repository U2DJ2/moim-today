package moim_today.persistence.entity.schedule;

import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.global.error.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_FORBIDDEN;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class ScheduleJpaEntityTest {

    @DisplayName("개인 일정을 수정한다.")
    @Test
    void updateSchedule() {
        // given
        long memberId = 1L;
        long meetingId = 2L;

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(1L)
                .scheduleName(NEW_SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.SATURDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        // when
        scheduleJpaEntity.updateSchedule(scheduleUpdateRequest);

        // then
        assertThat(scheduleJpaEntity.getMemberId()).isEqualTo(memberId);
        assertThat(scheduleJpaEntity.getMeetingId()).isEqualTo(meetingId);
        assertThat(scheduleJpaEntity.getScheduleName()).isEqualTo(NEW_SCHEDULE_NAME.value());
        assertThat(scheduleJpaEntity.getDayOfWeek()).isEqualTo(DayOfWeek.SATURDAY);
        assertThat(scheduleJpaEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 12, 0, 0));
        assertThat(scheduleJpaEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 14, 0, 0));
    }

    @DisplayName("회원 id가 일치하면 검증에 성공한다.")
    @Test
    void validateMember() {
        // given
        long memberId = 1L;
        long meetingId = 2L;

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        // expected
        assertThatCode(() -> scheduleJpaEntity.validateMember(memberId))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 id가 일치하지 않으면 예외가 발생한다.")
    @Test
    void validateMemberFail() {
        // given
        long memberId = 1L;
        long meetingId = 2L;

        long anotherMemberId = 9999L;

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        // expected
        assertThatCode(() -> scheduleJpaEntity.validateMember(anotherMemberId))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(SCHEDULE_FORBIDDEN.message());
    }
}