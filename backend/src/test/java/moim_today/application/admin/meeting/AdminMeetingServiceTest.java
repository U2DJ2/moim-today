package moim_today.application.admin.meeting;

import moim_today.dto.admin.meeting.AdminMeetingResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import moim_today.util.DatabaseCleaner;
import moim_today.util.TestConstant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_FORBIDDEN_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class AdminMeetingServiceTest {

    @Autowired
    private AdminMeetingService adminMeetingService;

    @Autowired
    private MoimRepository moimRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @DisplayName("모임 id에 맞는 미팅 정보를 찾는다.")
    @Test
    void findAllByMoimId() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
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
        List<AdminMeetingResponse> adminMeetingResponses =
                adminMeetingService.findAllByMoimId(UNIV_ID.longValue(), moimJpaEntity.getId());

        // then
        assertThat(adminMeetingResponses.size()).isEqualTo(2);
    }

    @DisplayName("모임 id에 맞는 미팅 정보를 찾는다.")
    @Test
    void findAllByMoimIdForbidden() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue() + 1)
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
        assertThatThrownBy(() -> adminMeetingService.findAllByMoimId(UNIV_ID.longValue(), moimJpaEntity.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MOIM_FORBIDDEN_ERROR.message());
    }

    @DisplayName("어드민 권한으로 미팅을 삭제한다.")
    @Test
    void deleteMeeting() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // when
        adminMeetingService.deleteMeeting(UNIV_ID.longValue(), meetingJpaEntity.getId());

        // then
        assertThat(meetingRepository.count()).isEqualTo(0);
    }

    @DisplayName("해당 학교의 관리자가 아니면 미팅을 삭제할 수 없다.")
    @Test
    void deleteMeetingForbidden() {
        // given 1
        long universityId = UNIV_ID.longValue();
        long otherUniversityId = UNIV_ID.longValue() + 1;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // expected
        assertThatThrownBy(() -> adminMeetingService.deleteMeeting(otherUniversityId, meetingJpaEntity.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MEETING_FORBIDDEN_ERROR.message());
    }
}