package moim_today.application.meeting.joined_meeting;

import moim_today.global.error.BadRequestException;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingComposition;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.implement.schedule.schedule.ScheduleComposition;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.*;


@Service
public class JoinedMeetingServiceImpl implements JoinedMeetingService {

    private final JoinedMeetingComposition joinedMeetingComposition;
    private final MoimFinder moimFinder;
    private final MeetingFinder meetingFinder;
    private final ScheduleComposition scheduleComposition;

    public JoinedMeetingServiceImpl(final JoinedMeetingComposition joinedMeetingComposition,
                                    final MoimFinder moimFinder,
                                    final MeetingFinder meetingFinder,
                                    final ScheduleComposition scheduleComposition) {
        this.joinedMeetingComposition = joinedMeetingComposition;
        this.moimFinder = moimFinder;
        this.meetingFinder = meetingFinder;
        this.scheduleComposition = scheduleComposition;
    }

    @Override
    public boolean findAttendanceStatus(final long memberId, final long meetingId) {
        return joinedMeetingComposition.findAttendanceStatus(memberId, meetingId);
    }

    @Transactional
    @Override
    public void acceptanceJoinMeeting(final long memberId, final long meetingId, final LocalDateTime currentDateTime) {
        MeetingJpaEntity meetingJpaEntity = meetingFinder.getById(meetingId);
        meetingJpaEntity.validateCurrentTime(currentDateTime);
        String moimTitle = moimFinder.getTitleById(meetingJpaEntity.getMoimId());
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.toEntity(memberId, moimTitle, meetingJpaEntity);
        boolean isNew = scheduleComposition.createScheduleIfNotExist(scheduleJpaEntity);

        if (isNew) {
            boolean attendance = true;
            joinedMeetingComposition.updateAttendance(memberId, meetingId, attendance);
        } else {
            throw new BadRequestException(SCHEDULE_ALREADY_EXIST.message());
        }
    }

    @Transactional
    @Override
    public void refuseJoinMeeting(final long memberId, final long meetingId, final LocalDateTime currentDateTime) {
        MeetingJpaEntity meetingJpaEntity = meetingFinder.getById(meetingId);
        meetingJpaEntity.validateCurrentTime(currentDateTime);
        boolean attendance = false;
        joinedMeetingComposition.updateAttendance(memberId, meetingId, attendance);
        scheduleComposition.deleteByMemberIdAndMeetingId(memberId, meetingId);
    }

    @Transactional
    @Override
    public void deleteAllByMeetingId(final long meetingId) {
        joinedMeetingComposition.deleteAllByMeetingId(meetingId);
    }
}
