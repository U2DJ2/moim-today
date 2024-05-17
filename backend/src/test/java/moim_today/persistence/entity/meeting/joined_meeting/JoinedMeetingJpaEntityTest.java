package moim_today.persistence.entity.meeting.joined_meeting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JoinedMeetingJpaEntityTest {

    @DisplayName("미팅 참석 여부 상태를 변경한다.")
    @Test
    void updateAttendance() {
        // given
        boolean beforeAttendanceStatus = false;
        boolean newAttendanceStatus = true;

        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .attendance(beforeAttendanceStatus)
                .build();

        // when
        joinedMeetingJpaEntity.updateAttendance(newAttendanceStatus);

        // then
        assertThat(joinedMeetingJpaEntity.isAttendance()).isTrue();
    }
}