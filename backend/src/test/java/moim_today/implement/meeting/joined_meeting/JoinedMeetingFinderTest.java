package moim_today.implement.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.util.ImplementTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.util.TestConstant.*;

class JoinedMeetingFinderTest extends ImplementTest {

    @Autowired
    private JoinedMeetingFinder joinedMeetingFinder;

    @DisplayName("미팅에 참여한 회원 id 목록을 가져온다.")
    @Test
    void findAllMemberId() {
        // given
        for (int i = 1; i < 11; i++) {
            JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                    .meetingId(MEETING_ID.intValue())
                    .memberId(i)
                    .build();

            joinedMeetingRepository.save(joinedMeetingJpaEntity);
        }

        // when
        List<Long> memberIds = joinedMeetingFinder.findAllMemberId(MEETING_ID.intValue());

        // then
        Assertions.assertThat(memberIds.size()).isEqualTo(10);
    }
}