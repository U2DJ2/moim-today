package moim_today.persistence.repository.meeting.joined_meeting;

import java.util.List;

public interface JoinedMeetingRepository {

    void deleteAllByMeetingIdIn(final List<Long> meetingIds);
}
