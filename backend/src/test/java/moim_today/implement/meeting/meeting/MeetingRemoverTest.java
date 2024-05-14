package moim_today.implement.meeting.meeting;

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

class MeetingRemoverTest extends ImplementTest {

    @Autowired
    private MeetingRemover meetingRemover;

    @DisplayName("미팅을 삭제한다.")
    @Test
    void deleteMeeting() {
        // given 1
        long memberId = MEMBER_ID.longValue();

        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(memberId)
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // when
        meetingRemover.deleteMeeting(memberId, meetingJpaEntity.getId());

        // then
        assertThat(meetingRepository.count()).isEqualTo(0);
    }

    @DisplayName("해당 미팅의 주최자가 아닌 사람이 삭제를 하면 예외가 발생한다.")
    @Test
    void validateHostId() {
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
                .build();

        meetingRepository.save(meetingJpaEntity);

        // then
        assertThatThrownBy(() -> meetingRemover.deleteMeeting(otherMemberId, meetingJpaEntity.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MEETING_FORBIDDEN_ERROR.message());
    }
}