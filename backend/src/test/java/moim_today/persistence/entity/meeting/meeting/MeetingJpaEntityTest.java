package moim_today.persistence.entity.meeting.meeting;

import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.TestConstant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_PAST_TIME_BAD_REQUEST_ERROR;
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

    @DisplayName("현재 시간이 미팅 시작 시간보다 앞에 있으면 검증에 성공한다.")
    @Test
    void validateCurrentTime() {
        // given
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .build();

        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 9, 59, 59);

        // when
        assertThatCode(() -> meetingJpaEntity.validateCurrentTime(currentDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("현재 시간이 미팅 시작 시간보다 뒤에 있으면 예외가 발생한다.")
    @Test
    void validateCurrentTimeFail() {
        // given
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .build();

        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 4, 10, 0, 1);

        // when
        assertThatThrownBy(() -> meetingJpaEntity.validateCurrentTime(currentDateTime))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MEETING_PAST_TIME_BAD_REQUEST_ERROR.message());
    }
}