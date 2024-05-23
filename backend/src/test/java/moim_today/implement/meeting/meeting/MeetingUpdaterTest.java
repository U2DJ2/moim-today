package moim_today.implement.meeting.meeting;

import moim_today.dto.meeting.meeting.MeetingUpdateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class MeetingUpdaterTest extends ImplementTest {

    @Autowired
    protected MeetingUpdater meetingUpdater;

    @DisplayName("미팅 정보를 업데이트한다.")
    @Test
    void updateMeeting() {
        // given 1
        long memberId = MEMBER_ID.longValue();

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 3
        String newAgenda = "newAgenda";
        String newPlace = "newPlace";

        MeetingUpdateRequest meetingUpdateRequest =
                MeetingUpdateRequest.of(meetingJpaEntity.getId(), newAgenda, newPlace);

        // when
        meetingUpdater.updateMeeting(memberId, meetingUpdateRequest);

        // then
        MeetingJpaEntity findEntity = meetingRepository.getById(meetingJpaEntity.getId());
        assertThat(findEntity.getAgenda()).isEqualTo(newAgenda);
        assertThat(findEntity.getPlace()).isEqualTo(newPlace);
    }

    @DisplayName("해당 미팅 생성자가 아니면 미팅을 수정할 수 없다.")
    @Test
    void updateMeetingFail() {
        // given 1
        long memberId = MEMBER_ID.longValue();
        long otherMemberId = 9999L;

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .agenda(MEETING_AGENDA.value())
                .place(MEETING_PLACE.value())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // given 3
        String newAgenda = "newAgenda";
        String newPlace = "newPlace";

        MeetingUpdateRequest meetingUpdateRequest =
                MeetingUpdateRequest.of(meetingJpaEntity.getId(), newAgenda, newPlace);

        // when && then
        assertThatThrownBy(() -> meetingUpdater.updateMeeting(otherMemberId, meetingUpdateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MEETING_FORBIDDEN_ERROR.message());
    }
}