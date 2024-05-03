package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import moim_today.util.TestConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static moim_today.util.TestConstant.*;
import static moim_today.util.TestConstant.MEETING_AGENDA;
import static moim_today.util.TestConstant.MEETING_PLACE;
import static org.assertj.core.api.Assertions.*;

class MeetingManagerTest extends ImplementTest {

    @Autowired
    private MeetingManager meetingManager;

    @DisplayName("단일 미팅을 생성한다.")
    @Test
    void createSingleMeeting() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // when
        meetingManager.createMeeting(meetingCreateRequest);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
    }

    @DisplayName("정기 미팅을 생성한다.")
    @Test
    void createRegularMeeting() {
        // given 1
        long memberId = 1;
        LocalDate startDate = LocalDate.of(2024, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.REGULAR)
                .build();

        // when
        meetingManager.createMeeting(meetingCreateRequest);

        // then
        long between = ChronoUnit.WEEKS.between(startDate, endDate) + 1;
        assertThat(meetingRepository.count()).isEqualTo(between);
    }
}