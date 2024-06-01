package moim_today.fake_class.meeting.joined_meeting;

import moim_today.application.meeting.joined_meeting.JoinedMeetingService;

import java.time.LocalDateTime;

public class FakeJoinedMeetingService implements JoinedMeetingService {

    @Override
    public boolean findAttendanceStatus(final long memberId, final long meetingId) {
        return false;
    }

    @Override
    public void acceptanceJoinMeeting(final long memberId, final long meetingId, final LocalDateTime currentDateTime) {

    }

    @Override
    public void refuseJoinMeeting(final long memberId, final long meetingId, final LocalDateTime currentDateTime) {

    }

    @Override
    public void deleteAllByMeetingId(final long meetingId) {

    }
}
