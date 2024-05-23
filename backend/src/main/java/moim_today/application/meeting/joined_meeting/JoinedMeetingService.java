package moim_today.application.meeting.joined_meeting;


public interface JoinedMeetingService {

    void acceptanceJoinMeeting(final long memberId, final long meetingId);

    void refuseJoinMeeting(final long memberId, final long meetingId);

    void deleteAllByMeetingId(final long meetingId);
}
