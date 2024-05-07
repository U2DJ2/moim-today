package moim_today.implement.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class JoinedMeetingAppenderTest extends ImplementTest {

    @Autowired
    private JoinedMeetingAppender joinedMeetingAppender;

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성한다.")
    @Test
    void saveJoinedMeeting() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        for (int i = 1; i < 11; i++) {
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .moimId(moimJpaEntity.getId())
                    .memberId(i)
                    .build();

            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        // given 3
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(moimJpaEntity.getId(), meetingJpaEntity.getId());

        // then
        assertThat(joinedMeetingRepository.count()).isEqualTo(10);
    }

    @DisplayName("모임 참여 정보를 바탕으로 미팅 참여 정보를 생성할 때 모두 참여 상태로 등록한다.")
    @Test
    void saveJoinedMeetingWithAttendance() {
        // given 1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // given 2
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .memberId(MEMBER_ID.longValue())
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        // given 3
        MeetingJpaEntity meetingJpaEntity = MeetingJpaEntity.builder()
                .moimId(moimJpaEntity.getId())
                .build();

        meetingRepository.save(meetingJpaEntity);

        // when
        joinedMeetingAppender.saveJoinedMeeting(moimJpaEntity.getId(), meetingJpaEntity.getId());

        // then
        List<JoinedMeetingJpaEntity> findEntities = joinedMeetingRepository.findAll();
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = findEntities.get(0);

        assertThat(joinedMeetingJpaEntity.isAttendance()).isTrue();
    }
}