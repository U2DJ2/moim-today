package moim_today.implement.meeting.joined_meeting;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.util.ImplementTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JoinedMeetingManagerTest extends ImplementTest {

    @Autowired
    private JoinedMeetingAppender joinedMeetingAppender;

    @DisplayName("모임 참여자를 바탕으로 미팅 참여 정보를 추가한다.")
    @Test
    void saveJoinedMeeting() {
        // given
        long moimId = 1L;
        long meetingId = 2L;

        for (long i = 0; i < 3; i++) {
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .memberId(i)
                    .moimId(moimId)
                    .build();

            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        // when
        joinedMeetingAppender.saveJoinedMeeting(moimId, meetingId);

        // then
        assertThat(joinedMeetingRepository.count()).isEqualTo(3);
    }
}