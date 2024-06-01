package moim_today.application.admin.meeting;

import moim_today.dto.admin.meeting.AdminMeetingResponse;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class AdminMeetingServiceTest {

    @Autowired
    private AdminMeetingService adminMeetingService;

    @Autowired
    private MoimRepository moimRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @DisplayName("모임 id에 맞는 미팅 정보를 찾는다.")
    @Test
    void findAllByMoimId() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity1 = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .build();

        MeetingJpaEntity meetingJpaEntity2 = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .build();

        MeetingJpaEntity otherMeetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId() + 1)
                .build();

        meetingRepository.save(meetingJpaEntity1);
        meetingRepository.save(meetingJpaEntity2);
        meetingRepository.save(otherMeetingJpaEntity);

        // when
        List<AdminMeetingResponse> adminMeetingResponses = adminMeetingService.findAllByMoimId(moimJpaEntity.getId());

        // then
        assertThat(adminMeetingResponses.size()).isEqualTo(2);
    }
}