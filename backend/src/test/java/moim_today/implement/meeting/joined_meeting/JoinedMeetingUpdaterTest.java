package moim_today.implement.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.util.ImplementTest;
import moim_today.util.TestConstant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;


class JoinedMeetingUpdaterTest extends ImplementTest {

    @Autowired
    private JoinedMeetingUpdater joinedMeetingUpdater;

    @DisplayName("회원 id, 미팅 id로 해당 미팅 참석 정보를 변경한다.")
    @Test
    void updateAttendance() {
        // given
        long memberId = TestConstant.MEMBER_ID.longValue();
        long meetingId = TestConstant.MEETING_ID.longValue();
        boolean beforeAttendanceStatus = false;
        boolean newAttendanceStatus = true;

        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .attendance(beforeAttendanceStatus)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        // when
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, newAttendanceStatus);

        // then
        JoinedMeetingJpaEntity findEntity = joinedMeetingRepository.getById(joinedMeetingJpaEntity.getId());
        assertThat(findEntity.isAttendance()).isEqualTo(newAttendanceStatus);
        assertThat(findEntity.getMemberId()).isEqualTo(memberId);
        assertThat(findEntity.getMeetingId()).isEqualTo(meetingId);
    }

    @DisplayName("다가오는 미팅 공지 메일 전송 여부를 수정한다.")
    @Test
    void updateUpcomingNoticeSent() {
        // given
        long memberId = TestConstant.MEMBER_ID.longValue();
        long meetingId = TestConstant.MEETING_ID.longValue();
        boolean beforeUpcomingNoticeSent = false;
        boolean afterUpcomingNoticeSent = true;

        JoinedMeetingJpaEntity joinedMeetingJpaEntity = JoinedMeetingJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .upcomingNoticeSent(beforeUpcomingNoticeSent)
                .build();

        joinedMeetingRepository.save(joinedMeetingJpaEntity);

        // when
        joinedMeetingUpdater.updateUpcomingNoticeSent(joinedMeetingJpaEntity.getId(), afterUpcomingNoticeSent);

        // then
        JoinedMeetingJpaEntity findEntity = joinedMeetingRepository.getById(joinedMeetingJpaEntity.getId());
        assertThat(findEntity.isUpcomingNoticeSent()).isEqualTo(afterUpcomingNoticeSent);
    }
}