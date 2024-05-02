package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.MEETING_AGENDA;
import static moim_today.util.TestConstant.MEETING_PLACE;
import static org.assertj.core.api.Assertions.*;

class MeetingAppenderTest extends ImplementTest {

    @Autowired
    private MeetingAppender meetingAppender;

    @DisplayName("단일 미팅을 생성한다.")
    @Test
    void createMeeting() {
        // given
        long moimId = 1L;

        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(moimId)
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        // when
        meetingAppender.createMeeting(meetingCreateRequest);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
    }
}