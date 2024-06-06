package moim_today.implement.schedule.schedule;

import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_FORBIDDEN;
import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_NOT_FOUND;
import static moim_today.util.TestConstant.MEETING_ID;
import static moim_today.util.TestConstant.MEMBER_ID;
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

    @DisplayName("미팅에 있는 모든 스케줄을 삭제한다.")
    @Test
    void deleteAllByMeetingId() {
        // given 1
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 2
        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);

        // when
        scheduleRemover.deleteAllByMeetingId(meetingJpaEntity.getId());

        // then
        assertThat(scheduleRepository.count()).isEqualTo(0);
    }

    @DisplayName("한 멤버의 미팅에 참여하는 기록들을 삭제한다")
    @Test
    void deleteAllByMemberInMeeting() {
        // given
        long meetingId1 = MEETING_ID.longValue();
        long meetingId2 = MEETING_ID.longValue() + 1L;
        long meetingId3 = MEETING_ID.longValue() + 2L;

        long forcedOutMemberId = MEMBER_ID.longValue() + 1L;

        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId1)
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId2)
                .build();

        ScheduleJpaEntity scheduleJpaEntity3 = ScheduleJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId3)
                .build();

        ScheduleJpaEntity savedSchedule = scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);
        scheduleRepository.save(scheduleJpaEntity3);

        List<Long> meetings = List.of(meetingId1, meetingId2, meetingId3);

        // when
        scheduleRepository.deleteAllByMemberInMeeting(forcedOutMemberId, meetings);

        // then
        assertThat(scheduleRepository.count()).isEqualTo(0L);
        assertThatThrownBy(() -> scheduleRepository.getById(savedSchedule.getId()))
                .hasMessage(SCHEDULE_NOT_FOUND.message());
    }

    @DisplayName("한 사람의 미팅 스케쥴들을 삭제할 때, 다른 미팅의 스케쥴과 다른 사람의 미팅 스케쥴은 삭제하지 않는다")
    @Test
    void deleteOnlySpecifiedMemberOfMeetingSchedule(){
        // given
        long meetingId1 = MEETING_ID.longValue();
        long meetingId2 = MEETING_ID.longValue() + 1L;
        long meetingId3 = MEETING_ID.longValue() + 3L;

        long forcedOutMemberId = MEMBER_ID.longValue();
        long stillInMemberId = MEMBER_ID.longValue() + 1L;

        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId1)
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId2)
                .build();

        ScheduleJpaEntity scheduleJpaEntity3 = ScheduleJpaEntity.builder()
                .memberId(forcedOutMemberId)
                .meetingId(meetingId3)
                .build();

        ScheduleJpaEntity scheduleJpaEntity4 = ScheduleJpaEntity.builder()
                .memberId(stillInMemberId)
                .meetingId(meetingId1)
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);
        ScheduleJpaEntity savedSchedule1 = scheduleRepository.save(scheduleJpaEntity3);
        ScheduleJpaEntity savedSchedule2 = scheduleRepository.save(scheduleJpaEntity4);

        List<Long> meetings = List.of(meetingId1, meetingId2);

        // when
        scheduleRepository.deleteAllByMemberInMeeting(forcedOutMemberId, meetings);

        // then
        assertThat(scheduleRepository.count()).isEqualTo(2L);
        assertThatCode(() -> scheduleRepository.getById(savedSchedule1.getId()))
                .doesNotThrowAnyException();
        assertThatCode(() -> scheduleRepository.getById(savedSchedule2.getId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 id와 미팅 id로 해당 스케줄을 삭제한다.")
    @Test
    void deleteByMemberIdAndMeetingId() {
        // given
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .meetingId(MEETING_ID.longValue())
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // when
        scheduleRemover.deleteByMemberIdAndMeetingId(MEMBER_ID.longValue(), MEETING_ID.longValue());

        // then
        assertThat(scheduleRepository.count()).isEqualTo(0);
    }

    @DisplayName("특정 회원 id를 가진 모든 스케줄을 삭제한다")
    @Test
    void deleteAllByMemberId() {
        // given
        ScheduleJpaEntity s1 = ScheduleJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .meetingId(MEETING_ID.longValue())
                .build();

        ScheduleJpaEntity s2 = ScheduleJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .meetingId(MEETING_ID.longValue())
                .build();

        // when
        scheduleRemover.deleteAllByMemberId(MEMBER_ID.longValue());

        // then
        assertThat(scheduleRepository.count()).isEqualTo(0);
    }
}
