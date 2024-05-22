package moim_today.implement.meeting.joined_meeting;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class JoinedMeetingRemover {

    private final JoinedMeetingRepository joinedMeetingRepository;

    public JoinedMeetingRemover(final JoinedMeetingRepository joinedMeetingRepository) {
        this.joinedMeetingRepository = joinedMeetingRepository;
    }

    @Transactional
    public void deleteAllByMeetingIdIn(final List<Long> meetingIds) {
        if (!meetingIds.isEmpty()) {
            joinedMeetingRepository.deleteAllByMeetingIdIn(meetingIds);
        }
    }

    @Transactional
    public void deleteAllByMeetingId(final long meetingId) {
        joinedMeetingRepository.deleteAllByMeetingId(meetingId);
    }

    @Transactional
    public void deleteAllByMemberInMeeting(final long memberId, final List<Long> meetingIds) {
        if(!meetingIds.isEmpty()){
            joinedMeetingRepository.deleteAllByMemberInMeeting(memberId, meetingIds);
        }
    }
}
