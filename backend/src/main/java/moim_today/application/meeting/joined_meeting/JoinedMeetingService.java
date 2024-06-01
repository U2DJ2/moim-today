package moim_today.application.meeting.joined_meeting;


import java.time.LocalDateTime;

public interface JoinedMeetingService {

    boolean findAttendanceStatus(final long memberId, final long meetingId);

    void acceptanceJoinMeeting(final long memberId, final long meetingId, final LocalDateTime currentDateTime);

    void refuseJoinMeeting(final long memberId, final long meetingId, final LocalDateTime currentDateTime);

    void deleteAllByMeetingId(final long meetingId);
}
