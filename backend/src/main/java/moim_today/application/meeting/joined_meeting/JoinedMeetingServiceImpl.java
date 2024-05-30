package moim_today.application.meeting.joined_meeting;

import moim_today.implement.meeting.joined_meeting.JoinedMeetingFinder;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingRemover;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingUpdater;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.implement.schedule.schedule.ScheduleAppender;
import moim_today.implement.schedule.schedule.ScheduleRemover;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JoinedMeetingServiceImpl implements JoinedMeetingService {

    private final JoinedMeetingFinder joinedMeetingFinder;
    private final JoinedMeetingUpdater joinedMeetingUpdater;
    private final JoinedMeetingRemover joinedMeetingRemover;
    private final MoimFinder moimFinder;
    private final MeetingFinder meetingFinder;
    private final ScheduleAppender scheduleAppender;
    private final ScheduleRemover scheduleRemover;

    public JoinedMeetingServiceImpl(final JoinedMeetingFinder joinedMeetingFinder,
                                    final JoinedMeetingUpdater joinedMeetingUpdater,
                                    final JoinedMeetingRemover joinedMeetingRemover,
                                    final MoimFinder moimFinder,
                                    final MeetingFinder meetingFinder,
                                    final ScheduleAppender scheduleAppender,
                                    final ScheduleRemover scheduleRemover) {
        this.joinedMeetingFinder = joinedMeetingFinder;
        this.joinedMeetingUpdater = joinedMeetingUpdater;
        this.joinedMeetingRemover = joinedMeetingRemover;
        this.moimFinder = moimFinder;
        this.meetingFinder = meetingFinder;
        this.scheduleAppender = scheduleAppender;
        this.scheduleRemover = scheduleRemover;
    }

    @Override
    public boolean findAttendanceStatus(final long memberId, final long meetingId) {
        return joinedMeetingFinder.findAttendanceStatus(memberId, meetingId);
    }

    @Transactional
    @Override
    public void acceptanceJoinMeeting(final long memberId, final long meetingId) {
        boolean attendance = true;
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, attendance);
        MeetingJpaEntity meetingJpaEntity = meetingFinder.getById(meetingId);
        String moimTitle = moimFinder.getTitleById(meetingJpaEntity.getMoimId());
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.toEntity(memberId, moimTitle, meetingJpaEntity);
        scheduleAppender.createScheduleIfNotExist(scheduleJpaEntity);
    }

    @Transactional
    @Override
    public void refuseJoinMeeting(final long memberId, final long meetingId) {
        boolean attendance = false;
        joinedMeetingUpdater.updateAttendance(memberId, meetingId, attendance);
        scheduleRemover.deleteByMemberIdAndMeetingId(memberId, meetingId);
    }

    @Transactional
    @Override
    public void deleteAllByMeetingId(final long meetingId) {
        joinedMeetingRemover.deleteAllByMeetingId(meetingId);
    }
}
