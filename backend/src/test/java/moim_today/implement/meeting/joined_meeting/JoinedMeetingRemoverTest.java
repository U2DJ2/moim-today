package moim_today.implement.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.util.ImplementTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static moim_today.util.TestConstant.MEETING_ID;
import static org.assertj.core.api.Assertions.*;

class JoinedMeetingRemoverTest extends ImplementTest {

    @Autowired
    private JoinedMeetingRemover joinedMeetingRemover;

    @DisplayName("미팅id가 리스트로 주어지면 해당되는 참여한 미팅 데이터를 모두 삭제한다.")
    @Test
    void deleteJoinedMeetingsByMeetingIdsTest() {

        // given
        long meetingId1 = Long.parseLong(MEETING_ID.value());
        long meetingId2 = Long.parseLong(MEETING_ID.value()) + 1L;
        long meetingId3 = Long.parseLong(MEETING_ID.value()) + 2L;

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
}