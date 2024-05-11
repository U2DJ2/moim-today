package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.MeetingSimpleDao;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.util.ImplementTest;
import moim_today.util.TestConstant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.util.TestConstant.*;
import static moim_today.util.TestConstant.MOIM_ID;
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
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingJpaEntity.getId())
                .memberId(memberJpaEntity2.getId())
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
        assertThat(meetingDetailResponse.members().size()).isEqualTo(2);
    }
}
