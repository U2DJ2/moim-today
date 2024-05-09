package moim_today.implement.meeting.meeting;

import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.*;

class MeetingFinderTest extends ImplementTest {

    @Autowired
    private MeetingFinder meetingFinder;
    
    @DisplayName("하나의 모임에 생성된 모든 미팅의 id를 반환한다.")
    @Test
    void findAllMeetingsByMoimIdTest(){
        //given
        long moimId1 = Long.parseLong(MOIM_ID.value());
        long moimId2 = Long.parseLong(MOIM_ID.value()) + 1L;

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
}
