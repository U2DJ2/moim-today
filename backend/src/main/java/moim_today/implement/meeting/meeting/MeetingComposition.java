package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.mail.UpcomingMeetingNoticeResponse;
import moim_today.dto.meeting.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.meeting.MeetingSimpleDao;
import moim_today.dto.meeting.meeting.MeetingUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Implement
public class MeetingComposition {

    private final MeetingAppender meetingAppender;
    private final MeetingFinder meetingFinder;
    private final MeetingUpdater meetingUpdater;
    private final MeetingRemover meetingRemover;

    public MeetingComposition(final MeetingAppender meetingAppender,
                              final MeetingFinder meetingFinder,
                              final MeetingUpdater meetingUpdater,
                              final MeetingRemover meetingRemover) {
        this.meetingAppender = meetingAppender;
        this.meetingFinder = meetingFinder;
        this.meetingUpdater = meetingUpdater;
        this.meetingRemover = meetingRemover;
    }

    public MeetingJpaEntity saveMeeting(final MeetingJpaEntity meetingJpaEntity) {
        return meetingAppender.saveMeeting(meetingJpaEntity);
    }

    public List<Long> findMeetingIdsByMoimId(final long moimId) {
        return meetingFinder.findMeetingIdsByMoimId(moimId);
    }

    public List<Long> findUpcomingMeetingIdsByMoimId(final long moimId, final LocalDate currentDate) {
        return meetingFinder.findUpcomingMeetingIdsByMoimId(moimId, currentDate);
    }

    public List<MeetingSimpleDao> findAllByMoimId(final long moimId, final long memberId,
                                                  final MeetingStatus meetingStatus) {
        return meetingFinder.findAllByMoimId(moimId, memberId, meetingStatus, LocalDateTime.now());
    }

    public MeetingDetailResponse findDetailsById(final long meetingId) {
        return meetingFinder.findDetailsById(meetingId);
    }

    public List<UpcomingMeetingNoticeResponse> findUpcomingNotices(final LocalDateTime currentDateTime) {
        return meetingFinder.findUpcomingNotices(currentDateTime);
    }

    public long getMoimIdByMeetingId(final long meetingId) {
        return meetingFinder.getMoimIdByMeetingId(meetingId);
    }

    public MeetingJpaEntity getById(final long meetingId) {
        return meetingFinder.getById(meetingId);
    }

    public void updateMeeting(final long memberId, final MeetingUpdateRequest meetingUpdateRequest) {
        meetingUpdater.updateMeeting(memberId, meetingUpdateRequest);
    }

    public void deleteMeeting(final long memberId, final long meetingId) {
        meetingRemover.deleteMeeting(memberId, meetingId);
    }
}