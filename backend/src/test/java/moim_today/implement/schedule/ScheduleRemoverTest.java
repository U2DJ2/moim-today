package moim_today.implement.schedule;

import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_FORBIDDEN;
import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_NOT_FOUND;
import static moim_today.util.TestConstant.MEETING_ID;
import static org.assertj.core.api.Assertions.*;

class ScheduleRemoverTest extends ImplementTest {

    @Autowired
    private ScheduleRemover scheduleRemover;

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
        scheduleRemover.deleteSchedule(memberId, scheduleJpaEntity.getId());

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
        assertThatCode(() ->  scheduleRemover.deleteSchedule(anotherMemberId, scheduleJpaEntity.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(SCHEDULE_FORBIDDEN.message());
    }

    @DisplayName("스케줄이 존재하지 않으면 삭제할 수 없다.")
    @Test
    void deleteScheduleNotFound() {
        // expected
        assertThatCode(() ->  scheduleRemover.deleteSchedule(1L, 9999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(SCHEDULE_NOT_FOUND.message());
    }

    @DisplayName("미팅id가 리스트로 주어지면 해당되는 스케줄 데이터를 모두 삭제한다.")
    @Test
    void deleteSchedulesByMeetingIdsTest() {

        // given
        long meetingId1 = Long.parseLong(MEETING_ID.value());
        long meetingId2 = Long.parseLong(MEETING_ID.value()) + 1L;
        long meetingId3 = Long.parseLong(MEETING_ID.value()) + 2L;

        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .meetingId(meetingId1)
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .meetingId(meetingId2)
                .build();

        ScheduleJpaEntity scheduleJpaEntity3 = ScheduleJpaEntity.builder()
                .meetingId(meetingId3)
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);
        scheduleRepository.save(scheduleJpaEntity3);

        List<Long> meetingIds = new ArrayList<>();
        meetingIds.add(meetingId1);
        meetingIds.add(meetingId2);

        //when
        scheduleRemover.deleteAllByMeetingIdIn(meetingIds);

        //then
        assertThat(scheduleRepository.count()).isEqualTo(1L);
    }
}
