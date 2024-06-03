package moim_today.implement.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class JoinedMeetingAppenderTest extends ImplementTest {

    @Autowired
    private JoinedMeetingAppender joinedMeetingAppender;

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성한다.")
    @Test
    void saveJoinedMeeting() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        for (int i = 1; i < 11; i++) {
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .moimId(moimJpaEntity.getId())
                    .memberId(i)
                    .build();

            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        // given 3
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(
                moimJpaEntity.getId(), meetingJpaEntity.getId(), moimJpaEntity.getTitle(), meetingJpaEntity
        );

        // then
        assertThat(joinedMeetingRepository.count()).isEqualTo(10);
    }

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성할 때 모두 참여 상태로 등록한다.")
    @Test
    void saveJoinedMeetingWithAttendance() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .memberId(MEMBER_ID.longValue())
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        // given 3
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(
                moimJpaEntity.getId(), meetingJpaEntity.getId(), moimJpaEntity.getTitle(), meetingJpaEntity
        );

        // then
        List<JoinedMeetingJpaEntity> findEntities = joinedMeetingRepository.findAll();
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = findEntities.get(0);

        assertThat(joinedMeetingJpaEntity.isAttendance()).isTrue();
    }

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성할 때 스케줄을 등록한다.")
    @Test
    void saveJoinedMeetingAndSchedules() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 2
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 3
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        // given 4
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(
                moimJpaEntity.getId(), meetingJpaEntity.getId(), moimJpaEntity.getTitle(), meetingJpaEntity
        );

        // then
        assertThat(scheduleRepository.count()).isEqualTo(1);
    }

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성할 때 이미 참여한 모임이면 스케줄을 생성하지 않는다.")
    @Test
    void saveAlreadyJoinedMeeting() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 2
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 3
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        // given 4
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 5
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(
                moimJpaEntity.getId(), meetingJpaEntity.getId(), moimJpaEntity.getTitle(), meetingJpaEntity
        );

        // then
        assertThat(scheduleRepository.count()).isEqualTo(0);
    }

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성할 때 이미 해당 스케줄에 일정이 있으면 미팅에 참여하지 않는다.")
    @Test
    void saveJoinedMeetingAlreadyExistOtherSchedule() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 2
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 3
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        // given 4
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 5
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .meetingId(meetingJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 9, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 11, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(
                moimJpaEntity.getId(), meetingJpaEntity.getId(), moimJpaEntity.getTitle(), meetingJpaEntity
        );

        // then
        assertThat(scheduleRepository.count()).isEqualTo(1);
        assertThat(joinedMeetingRepository.count()).isEqualTo(1);

        JoinedMeetingJpaEntity findEntity =
                joinedMeetingRepository.getByMemberIdAndMeetingId(memberJpaEntity.getId(), meetingJpaEntity.getId());

        assertThat(findEntity.isAttendance()).isFalse();
    }

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성할 때 다른 스케줄과 겹치지 않으면 미팅에 참여한다.")
    @Test
    void saveJoinedMeetingNotDuplicateOtherSchedule() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 2
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 3
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        // given 4
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 5
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .meetingId(meetingJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 5, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 5, 12, 0, 0))
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(
                moimJpaEntity.getId(), meetingJpaEntity.getId(), moimJpaEntity.getTitle(), meetingJpaEntity
        );

        // then
        assertThat(scheduleRepository.count()).isEqualTo(2);
        assertThat(joinedMeetingRepository.count()).isEqualTo(1);

        JoinedMeetingJpaEntity findEntity =
                joinedMeetingRepository.getByMemberIdAndMeetingId(memberJpaEntity.getId(), meetingJpaEntity.getId());

        assertThat(findEntity.isAttendance()).isTrue();
    }
}