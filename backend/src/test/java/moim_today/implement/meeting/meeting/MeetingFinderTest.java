package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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

        // when
        List<MeetingJpaEntity> meetingJpaEntities =
                meetingFinder.findAllByMoimId(moimId, MeetingStatus.UPCOMING, currentDateTime);

        // then
        assertThat(meetingJpaEntities.size()).isEqualTo(2);
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

        // when
        List<MeetingJpaEntity> meetingJpaEntities =
                meetingFinder.findAllByMoimId(moimId, MeetingStatus.PAST, currentDateTime);

        // then
        assertThat(meetingJpaEntities.size()).isEqualTo(2);
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

        // when
        List<MeetingJpaEntity> meetingJpaEntities =
                meetingFinder.findAllByMoimId(moimId, MeetingStatus.ALL, currentDateTime);

        // then
        assertThat(meetingJpaEntities.size()).isEqualTo(3);
    }
}
