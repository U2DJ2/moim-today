package moim_today.implement.schedule.schedule;

import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_ALREADY_EXIST;
import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_FORBIDDEN;
import static moim_today.util.TestConstant.*;
import static moim_today.util.TestConstant.SCHEDULE_NAME;
import static org.assertj.core.api.Assertions.*;


class ScheduleUpdaterTest extends ImplementTest {

    @Autowired
    private ScheduleUpdater scheduleUpdater;

    @DisplayName("개인 일정을 수정한다.")
    @Test
    void updateSchedule() {
        // given 1
        long memberId = 1L;
        long meetingId = 2L;

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // given 2
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(scheduleJpaEntity.getId())
                .scheduleName(NEW_SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 11, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 13, 0, 0))
                .build();

        // when
        scheduleUpdater.updateSchedule(memberId, scheduleUpdateRequest);

        // then
        ScheduleJpaEntity findEntity = scheduleRepository.getById(scheduleJpaEntity.getId());

        assertThat(scheduleRepository.count()).isEqualTo(1);
        assertThat(findEntity.getScheduleName()).isEqualTo(NEW_SCHEDULE_NAME.value());
        assertThat(findEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 11, 0, 0));
        assertThat(findEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 13, 0, 0));
    }

    @DisplayName("해당 시간대 앞에 스케줄이 존재하면 예외가 발생한다.")
    @Test
    void updateScheduleFailAlreadyExistBeforeSchedule() {
        // given 1
        long memberId = 1L;

        ScheduleJpaEntity BeforeScheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        scheduleRepository.save(BeforeScheduleJpaEntity);

        // given 2
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // given 2
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(scheduleJpaEntity.getId())
                .scheduleName(NEW_SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 11, 59, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        // expected
        assertThatCode(() -> scheduleUpdater.updateSchedule(memberId, scheduleUpdateRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(SCHEDULE_ALREADY_EXIST.message());

        assertThat(scheduleRepository.count()).isEqualTo(2);
    }

    @DisplayName("해당 시간대 뒤에 스케줄이 존재하면 예외가 발생한다.")
    @Test
    void updateScheduleFailAlreadyExistAfterSchedule() {
        // given 1
        long memberId = 1L;
        ScheduleJpaEntity afterScheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 16, 0, 0))
                .build();

        scheduleRepository.save(afterScheduleJpaEntity);

        // given 2
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // given 2
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(scheduleJpaEntity.getId())
                .scheduleName(NEW_SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 1, 0))
                .build();

        // expected
        assertThatCode(() -> scheduleUpdater.updateSchedule(memberId, scheduleUpdateRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(SCHEDULE_ALREADY_EXIST.message());

        assertThat(scheduleRepository.count()).isEqualTo(2);
    }

    @DisplayName("해당 회원의 스케줄이 아니면 예외가 발생한다.")
    @Test
    void validateMember() {
        // given 1
        long memberId = 1L;
        long anotherMemberId = 9999L;

        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // given 2
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(scheduleJpaEntity.getId())
                .scheduleName(NEW_SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 1, 0))
                .build();

        // expected
        assertThatCode(() -> scheduleUpdater.updateSchedule(anotherMemberId, scheduleUpdateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(SCHEDULE_FORBIDDEN.message());

        assertThat(scheduleRepository.count()).isEqualTo(1);
    }

    @DisplayName("다른 사용자의 스케줄은 영향을 주지 않는다.")
    @Test
    void notEffectOtherMemberSchedule() {
        // given 1
        long memberId = 1L;
        long anotherMemberId = 9999L;
        long meetingId = 2L;

        ScheduleJpaEntity otherScheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(anotherMemberId)
                .meetingId(meetingId)
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        scheduleRepository.save(otherScheduleJpaEntity);

        // given 2
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // given 2
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(scheduleJpaEntity.getId())
                .scheduleName(NEW_SCHEDULE_NAME.value())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 11, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 13, 0, 0))
                .build();

        // when
        scheduleUpdater.updateSchedule(memberId, scheduleUpdateRequest);

        // then
        ScheduleJpaEntity findEntity = scheduleRepository.getById(scheduleJpaEntity.getId());

        assertThat(scheduleRepository.count()).isEqualTo(2);
        assertThat(findEntity.getMemberId()).isEqualTo(memberId);
        assertThat(findEntity.getMeetingId()).isEqualTo(meetingId);
        assertThat(findEntity.getScheduleName()).isEqualTo(NEW_SCHEDULE_NAME.value());
        assertThat(findEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 11, 0, 0));
        assertThat(findEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 13, 0, 0));
    }
}