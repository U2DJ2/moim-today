package moim_today.implement.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.*;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class ScheduleAppenderTest extends ImplementTest {

    @Autowired
    private ScheduleAppender scheduleAppender;

    @DisplayName("시간표 정보로 스케줄 정보를 저장한다.")
    @Test
    void batchUpdateSchedules() {
        // given 1
        Schedule schedule1 = Schedule.builder()
                .scheduleName("스케줄 1")
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        Schedule schedule2 = Schedule.builder()
                .scheduleName("스케줄 2")
                .dayOfWeek(DayOfWeek.THURSDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        Schedule schedule3 = Schedule.builder()
                .scheduleName("스케줄 3")
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        long memberId = 1L;

        // given 2
        List<Schedule> schedules = List.of(schedule1, schedule2, schedule3);

        // when
        scheduleAppender.batchUpdateSchedules(schedules, memberId);

        // then
        assertThat(scheduleRepository.count()).isEqualTo(3);
    }

    @DisplayName("개인 일정을 등록한다.")
    @Test
    void createSchedule() {
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

        // when
        scheduleAppender.createSchedule(scheduleJpaEntity);

        // then
        ScheduleJpaEntity findEntity = scheduleRepository.getById(scheduleJpaEntity.getId());

        assertThat(scheduleRepository.count()).isEqualTo(1);
        assertThat(findEntity.getMemberId()).isEqualTo(memberId);
        assertThat(findEntity.getMeetingId()).isEqualTo(meetingId);
        assertThat(findEntity.getScheduleName()).isEqualTo(SCHEDULE_NAME.value());
        assertThat(findEntity.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(findEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 10, 0, 0));
        assertThat(findEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 12, 0, 0));
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
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        scheduleRepository.save(otherScheduleJpaEntity);

        // given 2
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        // when
        scheduleAppender.createSchedule(scheduleJpaEntity);

        // then
        ScheduleJpaEntity findEntity = scheduleRepository.getById(scheduleJpaEntity.getId());

        assertThat(scheduleRepository.count()).isEqualTo(2);
        assertThat(findEntity.getMemberId()).isEqualTo(memberId);
        assertThat(findEntity.getMeetingId()).isEqualTo(meetingId);
        assertThat(findEntity.getScheduleName()).isEqualTo(SCHEDULE_NAME.value());
        assertThat(findEntity.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(findEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 10, 0, 0));
        assertThat(findEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 1, 12, 0, 0));
    }

    @DisplayName("다른 스케줄 사이에 있는 일정을 등록한다.")
    @Test
    void createScheduleBetweenOtherSchedules() {
        // given 1
        ScheduleJpaEntity BeforeScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        ScheduleJpaEntity afterScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .build();

        scheduleRepository.save(BeforeScheduleJpaEntity);
        scheduleRepository.save(afterScheduleJpaEntity);

        // given 2
        ScheduleJpaEntity newScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 14, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 16, 0, 0))
                .build();

        // then
        assertThatCode(() -> scheduleAppender.createSchedule(newScheduleJpaEntity))
                .doesNotThrowAnyException();

        assertThat(scheduleRepository.count()).isEqualTo(3);
    }

    @DisplayName("해당 시간대 앞에 스케줄이 존재하면 예외가 발생한다.")
    @Test
    void createScheduleFailAlreadyExistBeforeSchedule() {
        // given 1
        ScheduleJpaEntity BeforeScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        scheduleRepository.save(BeforeScheduleJpaEntity);

        // given 2
        ScheduleJpaEntity newScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 11, 59, 59))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 16, 0, 0))
                .build();

        // then
        assertThatCode(() -> scheduleAppender.createSchedule(newScheduleJpaEntity))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(SCHEDULE_ALREADY_EXIST.message());

        assertThat(scheduleRepository.count()).isEqualTo(1);
    }

    @DisplayName("해당 시간대 뒤에 스케줄이 존재하면 예외가 발생한다.")
    @Test
    void createScheduleFailAlreadyExistAfterSchedule() {
        // given 1
        ScheduleJpaEntity afterScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        scheduleRepository.save(afterScheduleJpaEntity);

        // given 2
        ScheduleJpaEntity newScheduleJpaEntity = ScheduleJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 1, 1, 8, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 1))
                .build();

        // then
        assertThatCode(() -> scheduleAppender.createSchedule(newScheduleJpaEntity))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(SCHEDULE_ALREADY_EXIST.message());

        assertThat(scheduleRepository.count()).isEqualTo(1);
    }
}