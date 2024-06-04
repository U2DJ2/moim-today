package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.meeting.MeetingSimpleDao;
import moim_today.dto.meeting.meeting.MeetingUpdateRequest;
import moim_today.global.annotation.Implement;

import java.time.LocalDateTime;
import java.util.List;

@Implement
public class MeetingComposition {

    private final MeetingFinder meetingFinder;
    private final MeetingUpdater meetingUpdater;
    private final MeetingRemover meetingRemover;

    public MeetingComposition(final MeetingFinder meetingFinder,
                              final MeetingUpdater meetingUpdater,
                              final MeetingRemover meetingRemover) {
        this.meetingFinder = meetingFinder;
        this.meetingUpdater = meetingUpdater;
        this.meetingRemover = meetingRemover;
    }

    public List<MeetingSimpleDao> findAllByMoimId(final long moimId, final long memberId,
                                                  final MeetingStatus meetingStatus) {
        return meetingFinder.findAllByMoimId(moimId, memberId, meetingStatus, LocalDateTime.now());
    }

    public MeetingDetailResponse findDetailsById(final long meetingId) {
        return meetingFinder.findDetailsById(meetingId);
    }

    public long getMoimIdByMeetingId(final long meetingId) {
        return meetingFinder.getMoimIdByMeetingId(meetingId);
    }

    public void updateMeeting(final long memberId, final MeetingUpdateRequest meetingUpdateRequest) {
        meetingUpdater.updateMeeting(memberId, meetingUpdateRequest);
    }

    public void deleteMeeting(final long memberId, final long meetingId) {
        meetingRemover.deleteMeeting(memberId, meetingId);
    }
}
