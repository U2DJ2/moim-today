package moim_today.implement.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static moim_today.util.TestConstant.MEETING_ID;
import static moim_today.util.TestConstant.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;

class JoinedMeetingRemoverTest extends ImplementTest {

    @Autowired
    private JoinedMeetingRemover joinedMeetingRemover;

    @DisplayName("미팅id가 리스트로 주어지면 해당되는 참여한 미팅 데이터를 모두 삭제한다")
    @Test
    void deleteJoinedMeetingsByMeetingIdsTest() {

        // given
        long meetingId1 = MEETING_ID.longValue();
        long meetingId2 = MEETING_ID.longValue() + 1L;
        long meetingId3 = MEETING_ID.longValue() + 2L;

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId1)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId2)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId3)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        List<Long> meetingIds = new ArrayList<>();
        meetingIds.add(meetingId1);
        meetingIds.add(meetingId2);

        //when
        joinedMeetingRemover.deleteAllByMeetingIdIn(meetingIds);

        //then
        assertThat(joinedMeetingRepository.count()).isEqualTo(1L);
    }

    @DisplayName("멤버가 참여한 미팅들을 삭제한다")
    @Test
    void deleteAllByMemberInMeeting() {
        // given
        long meetingId1 = MEETING_ID.longValue();
        long meetingId2 = MEETING_ID.longValue() + 1L;
        long meetingId3 = MEETING_ID.longValue() + 2L;
        long forcedOutMemberId = MEMBER_ID.longValue();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId1)
                .memberId(forcedOutMemberId)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId2)
                .memberId(forcedOutMemberId)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId3)
                .memberId(forcedOutMemberId)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        joinedMeetingRepository.save(joinedMeetingJpaEntity3);

        List<Long> meetingIds = List.of(meetingId1, meetingId2, meetingId3);

        // when
        joinedMeetingRemover.deleteAllByMemberInMeeting(forcedOutMemberId, meetingIds);

        // then
        assertThat(joinedMeetingRepository.count()).isEqualTo(0L);
    }

    @DisplayName("미팅에서 특정 멤버만 삭제된다")
    @Test
    void deleteOnlySpecifiedMemberInMeeting() {
        // given
        long meetingId1 = MEETING_ID.longValue();
        long meetingId2 = MEETING_ID.longValue() + 1L;
        long forcedOutMemberId = MEMBER_ID.longValue();
        long stillInMemberId = MEMBER_ID.longValue() + 1L;

        JoinedMeetingJpaEntity joinedMeetingJpaEntity1 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId1)
                .memberId(forcedOutMemberId)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity2 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId2)
                .memberId(forcedOutMemberId)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity3 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId1)
                .memberId(stillInMemberId)
                .build();

        JoinedMeetingJpaEntity joinedMeetingJpaEntity4 = JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId2)
                .memberId(stillInMemberId)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity1);
        joinedMeetingRepository.save(joinedMeetingJpaEntity2);
        JoinedMeetingJpaEntity stillMeeting1 = joinedMeetingRepository.save(joinedMeetingJpaEntity3);
        JoinedMeetingJpaEntity stillMeeting2 = joinedMeetingRepository.save(joinedMeetingJpaEntity4);

        List<Long> meetingIds = List.of(meetingId1, meetingId2);

        // when
        joinedMeetingRemover.deleteAllByMemberInMeeting(forcedOutMemberId, meetingIds);

        // then
        long findMeetingId1 = joinedMeetingRepository.findById(stillMeeting1.getId()).getMeetingId();
        assertThat(findMeetingId1).isEqualTo(stillMeeting1.getMeetingId());

        long findMeetingId2 = joinedMeetingRepository.findById(stillMeeting1.getId()).getMeetingId();
        assertThat(findMeetingId2).isEqualTo(stillMeeting2.getMeetingId());

        long findMeetingId3 = joinedMeetingRepository.findById(stillMeeting1.getId()).getMeetingId();
        assertThat(findMeetingId3).isEqualTo(stillInMemberId);

        long findMeetingId4 = joinedMeetingRepository.findById(stillMeeting1.getId()).getMeetingId();
        assertThat(findMeetingId4).isEqualTo(stillInMemberId);
    }
}