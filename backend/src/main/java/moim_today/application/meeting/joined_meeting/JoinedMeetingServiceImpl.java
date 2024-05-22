package moim_today.application.meeting.joined_meeting;

import moim_today.implement.meeting.joined_meeting.JoinedMeetingRemover;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingUpdater;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JoinedMeetingServiceImpl implements JoinedMeetingService {

    private final JoinedMeetingUpdater joinedMeetingUpdater;
    private final JoinedMeetingRemover joinedMeetingRemover;

    public JoinedMeetingServiceImpl(final JoinedMeetingUpdater joinedMeetingUpdater,
                                    final JoinedMeetingRemover joinedMeetingRemover) {
        this.joinedMeetingUpdater = joinedMeetingUpdater;
        this.joinedMeetingRemover = joinedMeetingRemover;
    }

    @Transactional
    @Override
    public void acceptanceJoinMeeting(final long memberId, final long meetingId) {
        boolean attendance = true;
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, attendance);
    }

    @Transactional
    @Override
    public void refuseJoinMeeting(final long memberId, final long meetingId) {
        boolean attendance = false;
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, attendance);
    }

    @Transactional
    @Override
    public void deleteAllByMeetingId(final long meetingId) {
        joinedMeetingRemover.deleteAllByMeetingId(meetingId);
    }
}
