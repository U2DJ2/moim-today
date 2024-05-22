package moim_today.fake_class.meeting.joined_meeting;

import moim_today.application.meeting.joined_meeting.JoinedMeetingService;

public class FakeJoinedMeetingService implements JoinedMeetingService {

    @Override
    public void acceptanceJoinMeeting(final long memberId, final long meetingId) {

    }

    @Override
    public void refuseJoinMeeting(final long memberId, final long meetingId) {

    }

    @Override
    public void deleteAllByMeetingId(final long meetingId) {

    }
}
