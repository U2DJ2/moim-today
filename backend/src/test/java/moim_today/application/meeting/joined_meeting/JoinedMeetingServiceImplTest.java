package moim_today.application.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import moim_today.persistence.repository.schedule.schedule.ScheduleRepository;
import moim_today.util.TestConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class JoinedMeetingServiceImplTest {

    @Autowired
    private JoinedMeetingService joinedMeetingService;

    @Autowired
    private MoimRepository moimRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JoinedMeetingRepository joinedMeetingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("미팅에 참여하면 미팅 참여 여부가 참석으로 변경된다.")
    @Test
    void acceptanceJoinMeeting() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(TestConstant.MOIM_TITLE.value())
                .startDate(LocalDate.of(2024, 3, 4))
                .endDate(LocalDate.of(2024, 6, 30))
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 30, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 30, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 4
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        // when
        joinedMeetingService.acceptanceJoinMeeting(memberJpaEntity.getId(), meetingJpaEntity.getId());

        // then
        JoinedMeetingJpaEntity findEntity = joinedMeetingRepository.getById(joinedMeetingJpaEntity.getId());
        assertThat(findEntity.isAttendance()).isTrue();
    }

    @DisplayName("미팅에 참여하면 스케줄 정보가 등록된다.")
    @Test
    void acceptanceJoinMeetingAndCreateSchedule() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(TestConstant.MOIM_TITLE.value())
                .startDate(LocalDate.of(2024, 3, 4))
                .endDate(LocalDate.of(2024, 6, 30))
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 30, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 30, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 4
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        // when
        joinedMeetingService.acceptanceJoinMeeting(memberJpaEntity.getId(), meetingJpaEntity.getId());

        // then
        List<ScheduleJpaEntity> scheduleJpaEntities = scheduleRepository.findAllByMemberId(memberJpaEntity.getId());
        ScheduleJpaEntity scheduleJpaEntity = scheduleJpaEntities.get(0);
        assertThat(scheduleJpaEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 30, 10, 0, 0));
        assertThat(scheduleJpaEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 30, 12, 0, 0));
        assertThat(scheduleJpaEntity.getScheduleName()).isEqualTo(moimJpaEntity.getTitle());
    }

    @DisplayName("미팅 참석을 거절하면 미팅 참석 여부가 불참으로 변경된다.")
    @Test
    void refuseJoinMeeting() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(TestConstant.MOIM_TITLE.value())
                .startDate(LocalDate.of(2024, 3, 4))
                .endDate(LocalDate.of(2024, 6, 30))
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 30, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 30, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 4
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        // given 5
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .meetingId(meetingJpaEntity.getId())
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // when
        joinedMeetingService.refuseJoinMeeting(memberJpaEntity.getId(), meetingJpaEntity.getId());

        // then
        JoinedMeetingJpaEntity findEntity = joinedMeetingRepository.getById(joinedMeetingJpaEntity.getId());
        assertThat(findEntity.isAttendance()).isFalse();
    }

    @DisplayName("미팅 참석을 거절하면 해당 회원의 스케줄에서 삭제된다.")
    @Test
    void refuseJoinMeetingDeleteSchedule() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(TestConstant.MOIM_TITLE.value())
                .startDate(LocalDate.of(2024, 3, 4))
                .endDate(LocalDate.of(2024, 6, 30))
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .startDateTime(LocalDateTime.of(2024, 3, 30, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 30, 12, 0, 0))
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 4
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        // given 5
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .meetingId(meetingJpaEntity.getId())
                .build();

        scheduleRepository.save(scheduleJpaEntity);

        // when
        joinedMeetingService.refuseJoinMeeting(memberJpaEntity.getId(), meetingJpaEntity.getId());

        // then
        assertThat(scheduleRepository.count()).isEqualTo(1);
    }
}