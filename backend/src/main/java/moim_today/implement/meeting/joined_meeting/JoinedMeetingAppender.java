package moim_today.implement.meeting.joined_meeting;

import moim_today.global.annotation.Implement;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Implement
public class JoinedMeetingAppender {

    private final JoinedMeetingRepository joinedMeetingRepository;
    private final JoinedMoimFinder joinedMoimFinder;

    public JoinedMeetingAppender(final JoinedMeetingRepository joinedMeetingRepository,
                                 final JoinedMoimFinder joinedMoimFinder) {
        this.joinedMeetingRepository = joinedMeetingRepository;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Transactional
    public void saveJoinedMeeting(final long moimId, final long meetingId) {
        List<Long> memberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);
        List<JoinedMeetingJpaEntity> joinedMeetings = new ArrayList<>();

        for (long memberId : memberIds) {
            JoinedMeetingJpaEntity joinedMeeting = JoinedMeetingJpaEntity.toEntity(meetingId, memberId, true);
            joinedMeetings.add(joinedMeeting);
        }

        joinedMeetingRepository.saveAll(joinedMeetings);
    }
}
