package moim_today.implement.meeting.meeting;

import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MeetingAppenderTest extends ImplementTest {

    @Autowired
    private MeetingAppender meetingAppender;

    @DisplayName("미팅 정보를 저장한다.")
    @Test
    void saveMeeting() {
        // given
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(MOIM_ID.longValue())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024,3,4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024,6,30, 10, 0, 0))
                .place(MEETING_PLACE.value())
                .build();

        // when
        MeetingJpaEntity saveEntity = meetingAppender.saveMeeting(meetingJpaEntity);

        // then
        assertThat(meetingRepository.count()).isEqualTo(1);
        assertThat(saveEntity.getMoimId()).isEqualTo(Long.valueOf(MOIM_ID.value()));
        assertThat(saveEntity.getAgenda()).isEqualTo(MEETING_AGENDA.value());
        assertThat(saveEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(2024,3,4, 10, 0, 0));
        assertThat(saveEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(2024,6,30, 10, 0, 0));
        assertThat(saveEntity.getPlace()).isEqualTo(MEETING_PLACE.value());
    }
}