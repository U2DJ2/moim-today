package moim_today.application.meeting.joined_meeting;

import moim_today.implement.meeting.joined_meeting.JoinedMeetingUpdater;
import org.springframework.stereotype.Service;

@Service
public class JoinedMeetingServiceImpl implements JoinedMeetingService {

    private final JoinedMeetingUpdater joinedMeetingUpdater;

    public JoinedMeetingServiceImpl(final JoinedMeetingUpdater joinedMeetingUpdater) {
        this.joinedMeetingUpdater = joinedMeetingUpdater;
    }

    @Override
    public void refuseJoinMeeting(final long memberId, final long meetingId) {
        boolean attendance = false;
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, attendance);
    }
}
