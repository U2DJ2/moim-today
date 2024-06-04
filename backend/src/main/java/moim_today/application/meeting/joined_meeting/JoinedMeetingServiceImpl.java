package moim_today.application.meeting.joined_meeting;

import moim_today.global.error.BadRequestException;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingComposition;
import moim_today.implement.meeting.meeting.MeetingComposition;
import moim_today.implement.moim.moim.MoimComposition;
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
    private final MoimComposition moimComposition;
    private final MeetingComposition meetingComposition;
    private final ScheduleComposition scheduleComposition;

    public JoinedMeetingServiceImpl(final JoinedMeetingComposition joinedMeetingComposition,
                                    final MoimComposition moimComposition,
                                    final MeetingComposition meetingComposition,
                                    final ScheduleComposition scheduleComposition) {
        this.joinedMeetingComposition = joinedMeetingComposition;
        this.moimComposition = moimComposition;
        this.meetingComposition = meetingComposition;
        this.scheduleComposition = scheduleComposition;
    }

    @Override
    public boolean findAttendanceStatus(final long memberId, final long meetingId) {
        return joinedMeetingComposition.findAttendanceStatus(memberId, meetingId);
    }

    @Transactional
    @Override
    public void acceptanceJoinMeeting(final long memberId, final long meetingId, final LocalDateTime currentDateTime) {
        MeetingJpaEntity meetingJpaEntity = meetingComposition.getById(meetingId);
        meetingJpaEntity.validateCurrentTime(currentDateTime);
        String moimTitle = moimComposition.getTitleById(meetingJpaEntity.getMoimId());
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
        MeetingJpaEntity meetingJpaEntity = meetingComposition.getById(meetingId);
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
