package moim_today.implement.meeting.joined_meeting;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;

import java.util.List;

@Implement
public class JoinedMeetingRemover {

    private final JoinedMeetingRepository joinedMeetingRepository;

    public JoinedMeetingRemover(final JoinedMeetingRepository joinedMeetingRepository) {
        this.joinedMeetingRepository = joinedMeetingRepository;
    }

    public void deleteAllByMeetingIdIn(final List<Long> meetingIds) {
        joinedMeetingRepository.deleteAllByMeetingIdIn(meetingIds);
    }
}
