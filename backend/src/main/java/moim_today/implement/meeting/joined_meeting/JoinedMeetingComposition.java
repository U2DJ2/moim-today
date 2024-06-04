package moim_today.implement.meeting.joined_meeting;

import moim_today.dto.member.MemberSimpleResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.util.List;

@Implement
public class JoinedMeetingComposition {

    private final JoinedMeetingAppender joinedMeetingAppender;
    private final JoinedMeetingFinder joinedMeetingFinder;
    private final JoinedMeetingUpdater joinedMeetingUpdater;
    private final JoinedMeetingRemover joinedMeetingRemover;

    public JoinedMeetingComposition(final JoinedMeetingAppender joinedMeetingAppender,
                                    final JoinedMeetingFinder joinedMeetingFinder,
                                    final JoinedMeetingUpdater joinedMeetingUpdater,
                                    final JoinedMeetingRemover joinedMeetingRemover) {
        this.joinedMeetingAppender = joinedMeetingAppender;
        this.joinedMeetingFinder = joinedMeetingFinder;
        this.joinedMeetingUpdater = joinedMeetingUpdater;
        this.joinedMeetingRemover = joinedMeetingRemover;
    }

    public void saveJoinedMeeting(final long moimId, final long meetingId,
                                  final String moimTitle, final MeetingJpaEntity meetingJpaEntity) {
        joinedMeetingAppender.saveJoinedMeeting(moimId, meetingId, moimTitle, meetingJpaEntity);
    }

    public boolean findAttendanceStatus(final long memberId, final long meetingId) {
        return joinedMeetingFinder.findAttendanceStatus(memberId, meetingId);
    }

    public List<Long> findAllMemberId(final long meetingId) {
        return joinedMeetingFinder.findAllMemberId(meetingId);
    }

    public List<MemberSimpleResponse> findMembersJoinedMeeting(final long meetingId) {
        return joinedMeetingFinder.findMembersJoinedMeeting(meetingId);
    }

    public void updateAttendance(final long memberId, final long meetingId, final boolean attendance) {
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, attendance);
    }

    public void updateUpcomingNoticeSent(final long joinedMeetingId, final boolean upcomingNoticeSent) {
        joinedMeetingUpdater.updateUpcomingNoticeSent(joinedMeetingId, upcomingNoticeSent);
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
