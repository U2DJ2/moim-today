package moim_today.persistence.entity.meeting.meeting;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.TestConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class MeetingJpaEntityTest {

    @DisplayName("미팅 정보를 수정한다.")
    @Test
    void updateMeeting() {
        // given
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .agenda(MEETING_AGENDA.value())
                .place(MEETING_PLACE.value())
                .build();

        String newAgenda = "newAgenda";
        String newPlace = "newPlace";

        // when
        meetingJpaEntity.updateMeeting(newAgenda, newPlace);

        // then
        assertThat(meetingJpaEntity.getAgenda()).isEqualTo(newAgenda);
        assertThat(meetingJpaEntity.getPlace()).isEqualTo(newPlace);
    }
}