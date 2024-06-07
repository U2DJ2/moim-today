package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.mail.UpcomingMeetingNoticeResponse;
import moim_today.dto.meeting.meeting.JoinedMeetingResponse;
import moim_today.dto.meeting.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.meeting.MeetingSimpleDao;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import moim_today.util.TestConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MeetingFinderTest extends ImplementTest {

    @Autowired
    private MeetingFinder meetingFinder;
    
    @DisplayName("하나의 모임에 생성된 모든 미팅의 id를 반환한다.")
    @Test
    void findAllMeetingsByMoimIdTest(){
        //given
        long moimId1 = MOIM_ID.longValue();
        long moimId2 = MOIM_ID.longValue() + 1L;

        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimId1)
                .build();

        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimId1)
                .build();

        MeetingJpaEntity meetingJpaEntity3 = MeetingJpaEntity.builder()
                .moimId(moimId2)
                .build();

        meetingRepository.save(meetingJpaEntity1);
        meetingRepository.save(meetingJpaEntity2);
        meetingRepository.save(meetingJpaEntity3);

        //when
        List<Long> meetingIds = meetingFinder.findMeetingIdsByMoimId(moimId1);

        //then
        assertThat(meetingIds.size()).isEqualTo(2);
    }

    @DisplayName("하나의 모임에 생성된 이후에 다가올 미팅의 엔티티 정보를 반환한다.")
    @Test
    void findAllUpcomingByMoimId() {
        // given 1
        long moimId = MOIM_ID.longValue();
        LocalDateTime pastDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);
        LocalDateTime upcomingDateTime = LocalDateTime.of(2024, 3, 4, 12, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 10, 0, 0);

        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(upcomingDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(upcomingDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity3 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(pastDateTime)
                .build();

        meetingRepository.save(meetingJpaEntity1);
        meetingRepository.save(meetingJpaEntity2);
        meetingRepository.save(meetingJpaEntity3);

        // given 2
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity2.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity3.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        // when
        List<MeetingSimpleDao> meetingSimpleDaos =
                meetingFinder.findAllByMoimId(moimId, memberId, MeetingStatus.UPCOMING, currentDateTime);

        // then
        assertThat(meetingSimpleDaos.size()).isEqualTo(2);
    }

    @DisplayName("다가올 미팅의 엔티티 정보를 생성일자가 오래된 순으로 반환한다.")
    @Test
    void findAllUpcomingByMoimIdOrderByStartDateTimeAsc() {
        // given 1
        long moimId = MOIM_ID.longValue();
        LocalDateTime pastDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);
        LocalDateTime upcomingDateTime = LocalDateTime.of(2024, 3, 4, 12, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 10, 0, 0);

        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .agenda(FIRST_CREATED_MEETING_AGENDA.value())
                .startDateTime(upcomingDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .agenda(SECOND_CREATED_MEETING_AGENDA.value())
                .startDateTime(upcomingDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity3 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(pastDateTime)
                .build();

        meetingRepository.save(meetingJpaEntity1);
        meetingRepository.save(meetingJpaEntity2);
        meetingRepository.save(meetingJpaEntity3);

        // given 2
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity2.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity3.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        // when
        List<MeetingSimpleDao> meetingSimpleDaos =
                meetingFinder.findAllByMoimId(moimId, memberId, MeetingStatus.UPCOMING, currentDateTime);

        // then
        assertThat(meetingSimpleDaos.size()).isEqualTo(2);
        assertThat(meetingSimpleDaos.get(0).agenda()).isEqualTo(FIRST_CREATED_MEETING_AGENDA.value());
        assertThat(meetingSimpleDaos.get(1).agenda()).isEqualTo(SECOND_CREATED_MEETING_AGENDA.value());
    }

    @DisplayName("하나의 모임에 생성된 이후에 이전 미팅의 엔티티 정보를 반환한다.")
    @Test
    void findAllPastByMoimId() {
        // given 1
        long moimId = MOIM_ID.longValue();
        LocalDateTime pastDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);
        LocalDateTime upcomingDateTime = LocalDateTime.of(2024, 3, 4, 12, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 10, 0, 0);

        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(pastDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(pastDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity3 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(upcomingDateTime)
                .build();

        meetingRepository.save(meetingJpaEntity1);
        meetingRepository.save(meetingJpaEntity2);
        meetingRepository.save(meetingJpaEntity3);

        // given 2
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity2.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity3.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        // when
        List<MeetingSimpleDao> meetingSimpleDaos =
                meetingFinder.findAllByMoimId(moimId, memberId, MeetingStatus.PAST, currentDateTime);

        // then
        assertThat(meetingSimpleDaos.size()).isEqualTo(2);
    }

    @DisplayName("이전 미팅의 엔티티 정보를 생성일자가 오래된 순으로 반환한다.")
    @Test
    void findAllPastByMoimIdOrderByStartDateTimeAsc() {
        // given 1
        long moimId = MOIM_ID.longValue();
        LocalDateTime pastDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);
        LocalDateTime upcomingDateTime = LocalDateTime.of(2024, 3, 4, 12, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 10, 0, 0);

        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .agenda(FIRST_CREATED_MEETING_AGENDA.value())
                .startDateTime(pastDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .agenda(SECOND_CREATED_MEETING_AGENDA.value())
                .startDateTime(pastDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity3 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(upcomingDateTime)
                .build();

        meetingRepository.save(meetingJpaEntity1);
        meetingRepository.save(meetingJpaEntity2);
        meetingRepository.save(meetingJpaEntity3);

        // given 2
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity2.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity3.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        // when
        List<MeetingSimpleDao> meetingSimpleDaos =
                meetingFinder.findAllByMoimId(moimId, memberId, MeetingStatus.PAST, currentDateTime);

        // then
        assertThat(meetingSimpleDaos.size()).isEqualTo(2);
        assertThat(meetingSimpleDaos.get(0).agenda()).isEqualTo(FIRST_CREATED_MEETING_AGENDA.value());
        assertThat(meetingSimpleDaos.get(1).agenda()).isEqualTo(SECOND_CREATED_MEETING_AGENDA.value());
    }

    @DisplayName("하나의 모임의 모든 미팅의 엔티티 정보를 반환한다.")
    @Test
    void findAllByMoimId() {
        // given 1
        long moimId = MOIM_ID.longValue();
        LocalDateTime pastDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);
        LocalDateTime upcomingDateTime = LocalDateTime.of(2024, 3, 4, 12, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 10, 0, 0);

        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(pastDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(pastDateTime)
                .build();

        MeetingJpaEntity meetingJpaEntity3 = MeetingJpaEntity.builder()
                .moimId(moimId)
                .startDateTime(upcomingDateTime)
                .build();

        meetingRepository.save(meetingJpaEntity1);
        meetingRepository.save(meetingJpaEntity2);
        meetingRepository.save(meetingJpaEntity3);

        // given 2
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity2.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingJpaEntity3.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        // when
        List<MeetingSimpleDao> meetingSimpleDaos =
                meetingFinder.findAllByMoimId(moimId, memberId, MeetingStatus.ALL, currentDateTime);

        // then
        assertThat(meetingSimpleDaos.size()).isEqualTo(3);
    }

    @DisplayName("미팅 id로 해당 미팅의 상세 정보를 가져온다.")
    @Test
    void findDetailsById() {
        // given 1
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 2
        MemberJpaEntity memberJpaEntity1 = MemberJpaEntity.builder()
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        MemberJpaEntity memberJpaEntity2 = MemberJpaEntity.builder()
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        memberRepository.save(memberJpaEntity1);
        memberRepository.save(memberJpaEntity2);

        // given 3
        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity2.getId())
                .attendance(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);

        // when
        MeetingDetailResponse meetingDetailResponse = meetingFinder.findDetailsById(meetingJpaEntity.getId());

        // then
        assertThat(meetingDetailResponse.meetingId()).isEqualTo(meetingJpaEntity.getId());
        assertThat(meetingDetailResponse.agenda()).isEqualTo(MEETING_AGENDA.value());
        assertThat(meetingDetailResponse.startDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 10, 0, 0));
        assertThat(meetingDetailResponse.endDateTime()).isEqualTo(LocalDateTime.of(2024, 3, 4, 12, 0, 0));
        assertThat(meetingDetailResponse.place()).isEqualTo(MEETING_PLACE.value());
        assertThat(meetingDetailResponse.members().size()).isEqualTo(1);
    }

    @DisplayName("다가오는 미팅 정보들을 조회한다.")
    @Test
    void findUpcomingNotices() {
        // given 1
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);

        // given 2
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // given 3
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 4
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .subscribeStatus(true)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        // then
        UpcomingMeetingNoticeResponse upcomingNotice = upcomingNotices.get(0);

        assertThat(upcomingNotices.size()).isEqualTo(1);
        assertThat(upcomingNotice.email()).isEqualTo(memberJpaEntity.getEmail());
        assertThat(upcomingNotice.agenda()).isEqualTo(meetingJpaEntity.getAgenda());
        assertThat(upcomingNotice.startDateTime()).isEqualTo(meetingJpaEntity.getStartDateTime());
        assertThat(upcomingNotice.endDateTime()).isEqualTo(meetingJpaEntity.getEndDateTime());
        assertThat(upcomingNotice.place()).isEqualTo(meetingJpaEntity.getPlace());
        assertThat(upcomingNotice.attendance()).isEqualTo(joinedMeetingJpaEntity.isAttendance());
    }

    @DisplayName("이메일 수신 여부를 수락한 사람만 다가오는 미팅 정보를 조회한다.")
    @Test
    void findUpcomingNoticesOnlySubscribeStatusTrue() {
        // given 1
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);

        // given 2
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // given 3
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 4
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .subscribeStatus(true)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        // then
        assertThat(upcomingNotices.size()).isEqualTo(1);
    }

    @DisplayName("이메일 수신 여부를 거절한 사람은 다가오는 미팅 정보가 조회되지 않는다.")
    @Test
    void findUpcomingNoticesOnlySubscribeStatusFalse() {
        // given 1
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);

        // given 2
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // given 3
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 4
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .subscribeStatus(false)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        // then
        assertThat(upcomingNotices.size()).isEqualTo(0);
    }

    @DisplayName("다가오는 미팅은 시작 시간이 현재 시간보다 뒤에 있는 시간대를 조회한다.")
    @Test
    void findUpcomingNoticesOnlyAfter() {
        // given 1
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);

        // given 2
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // given 3
        MeetingJpaEntity beforeMeetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 8, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity afterMeetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 8, 0, 1))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(beforeMeetingJpaEntity);
        meetingRepository.save(afterMeetingJpaEntity);

        // given 4
        JoinedMeetingJpaEntity beforeJoinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(beforeMeetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        JoinedMeetingJpaEntity afterJoinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(afterMeetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        joinedMeetingRepository.save(beforeJoinedMeetingJpaEntity);
        joinedMeetingRepository.save(afterJoinedMeetingJpaEntity);

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .subscribeStatus(true)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        // then
        assertThat(upcomingNotices.size()).isEqualTo(1);
    }

    @DisplayName("다가오는 미팅은 시작시간 기준으로 24시간 이내의 미팅만 조회한다.")
    @Test
    void findUpcomingNoticesUntilAfter24Hours() {
        // given 1
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);

        // given 2
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // given 3
        MeetingJpaEntity beforeMeetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 5, 8, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 5, 10, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity afterMeetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 5, 8, 0, 1))
                .endDateTime(LocalDateTime.of(2024, 3, 5, 10, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(beforeMeetingJpaEntity);
        meetingRepository.save(afterMeetingJpaEntity);

        // given 4
        JoinedMeetingJpaEntity beforeJoinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(beforeMeetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        JoinedMeetingJpaEntity afterJoinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(afterMeetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        joinedMeetingRepository.save(beforeJoinedMeetingJpaEntity);
        joinedMeetingRepository.save(afterJoinedMeetingJpaEntity);

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .subscribeStatus(true)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        // then
        assertThat(upcomingNotices.size()).isEqualTo(1);
    }

    @DisplayName("다가오는 미팅중에서 공지 메일을 보내지 않은 미팅 정보만 가져온다.")
    @Test
    void findUpcomingNoticesOnlyUpcomingNoticeSentFalse() {
        // given 1
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 8, 0, 0);

        // given 2
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // given 3
        MeetingJpaEntity beforeMeetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity afterMeetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 14, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(beforeMeetingJpaEntity);
        meetingRepository.save(afterMeetingJpaEntity);

        // given 4
        JoinedMeetingJpaEntity beforeJoinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(beforeMeetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(false)
                .build();

        JoinedMeetingJpaEntity afterJoinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .meetingId(afterMeetingJpaEntity.getId())
                .memberId(memberJpaEntity.getId())
                .attendance(true)
                .upcomingNoticeSent(true)
                .build();

        joinedMeetingRepository.save(beforeJoinedMeetingJpaEntity);
        joinedMeetingRepository.save(afterJoinedMeetingJpaEntity);

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .subscribeStatus(true)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        List<UpcomingMeetingNoticeResponse> upcomingNotices = meetingFinder.findUpcomingNotices(currentDateTime);

        // then
        assertThat(upcomingNotices.size()).isEqualTo(1);
    }

    @DisplayName("미팅 Id로 모임 Id를 조회한다.")
    @Test
    void getMoimIdByMeetingId() {
        // given
        long expectedMoimId = MOIM_ID.longValue();

        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(expectedMoimId)
                .build();

        meetingRepository.save(meetingJpaEntity);
        long meetingId = meetingJpaEntity.getId();

        // when
        long actualMoimId = meetingFinder.getMoimIdByMeetingId(meetingId);

        // then
        assertThat(expectedMoimId).isEqualTo(actualMoimId);
    }

    @DisplayName("미팅 Id로 모임 Id를 조회할때, 미팅이 존재하지 않으면 예외가 발생한다.")
    @Test
    void getMoimIdByMeetingIdThrowsNotFoundException() {
        // expected
        assertThatThrownBy(() -> meetingFinder.getMoimIdByMeetingId(MEETING_ID.longValue()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MEETING_NOT_FOUND_ERROR.message());
    }

    @DisplayName("회원이 참여한 미팅 정보를 가져온다.")
    @Test
    void findAllByMeetingIds() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity beforeMeetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity afterMeetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 14, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(beforeMeetingJpaEntity);
        meetingRepository.save(afterMeetingJpaEntity);

        // given 2
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(beforeMeetingJpaEntity.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(afterMeetingJpaEntity.getId())
                .attendance(true)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);

        // when
        List<JoinedMeetingResponse> joinedMeetingResponses = meetingFinder.findAllByMemberId(
                memberId, MeetingStatus.ALL,
                LocalDateTime.of(2024, 3, 4, 12, 0, 0)
        );

        // then
        assertThat(joinedMeetingResponses.size()).isEqualTo(2);
    }

    @DisplayName("회원이 참여한 다가오는 미팅 정보를 가져온다.")
    @Test
    void findAllUpcomingByMeetingIds() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity beforeMeetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 11, 59, 59))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity afterMeetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 1))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 14, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity afterMeetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 1))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 14, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(beforeMeetingJpaEntity);
        meetingRepository.save(afterMeetingJpaEntity1);
        meetingRepository.save(afterMeetingJpaEntity2);

        // given 3
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(beforeMeetingJpaEntity.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(afterMeetingJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(afterMeetingJpaEntity2.getId())
                .attendance(true)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        // when
        List<JoinedMeetingResponse> joinedMeetingResponses = meetingFinder.findAllByMemberId(
                memberId, MeetingStatus.UPCOMING,
                LocalDateTime.of(2024, 3, 4, 12, 0, 0)
        );

        // then
        assertThat(joinedMeetingResponses.size()).isEqualTo(2);
    }

    @DisplayName("회원이 참여한 미팅 정보를 가져온다.")
    @Test
    void findAllPastByMeetingIds() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity beforeMeetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 11, 59, 59))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity beforeMeetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 11, 59, 59))
                .place(MEETING_PLACE.value())
                .build();

        MeetingJpaEntity afterMeetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 1))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 14, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(beforeMeetingJpaEntity1);
        meetingRepository.save(beforeMeetingJpaEntity2);
        meetingRepository.save(afterMeetingJpaEntity);

        // given 3
        long memberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(beforeMeetingJpaEntity1.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(beforeMeetingJpaEntity2.getId())
                .attendance(true)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(afterMeetingJpaEntity.getId())
                .attendance(true)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        // when
        List<JoinedMeetingResponse> joinedMeetingResponses = meetingFinder.findAllByMemberId(
                memberId, MeetingStatus.PAST,
                LocalDateTime.of(2024, 3, 4, 12, 0, 0)
        );

        // then
        assertThat(joinedMeetingResponses.size()).isEqualTo(2);
    }
}
