package moim_today.implement.meeting.joined_meeting;

import moim_today.global.annotation.Implement;

import java.util.List;

@Implement
public class JoinedMeetingComposition {

    private final JoinedMeetingFinder joinedMeetingFinder;
    private final JoinedMeetingUpdater joinedMeetingUpdater;
    private final JoinedMeetingRemover joinedMeetingRemover;

    public JoinedMeetingComposition(final JoinedMeetingFinder joinedMeetingFinder,
                                    final JoinedMeetingUpdater joinedMeetingUpdater,
                                    final JoinedMeetingRemover joinedMeetingRemover) {
        this.joinedMeetingFinder = joinedMeetingFinder;
        this.joinedMeetingUpdater = joinedMeetingUpdater;
        this.joinedMeetingRemover = joinedMeetingRemover;
    }

    public boolean findAttendanceStatus(final long memberId, final long meetingId) {
        return joinedMeetingFinder.findAttendanceStatus(memberId, meetingId);
    }

    public void updateAttendance(final long memberId, final long meetingId, final boolean attendance) {
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, attendance);
    }

    public void deleteAllByMeetingIdIn(final List<Long> meetingIds) {
        joinedMeetingRemover.deleteAllByMeetingIdIn(meetingIds);
    }

    public void deleteAllByMeetingId(final long meetingId) {
        joinedMeetingRemover.deleteAllByMeetingId(meetingId);
    }

    public void deleteAllByMemberInMeeting(final long memberId, final List<Long> meetingIds) {
        joinedMeetingRemover.deleteAllByMemberInMeeting(memberId, meetingIds);
    }
}
