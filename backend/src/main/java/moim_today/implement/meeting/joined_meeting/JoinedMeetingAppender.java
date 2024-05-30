package moim_today.implement.meeting.joined_meeting;

import moim_today.global.annotation.Implement;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.schedule.schedule.ScheduleAppender;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class JoinedMeetingAppender {

    private final JoinedMeetingRepository joinedMeetingRepository;
    private final JoinedMoimFinder joinedMoimFinder;
    private final ScheduleAppender scheduleAppender;

    public JoinedMeetingAppender(final JoinedMeetingRepository joinedMeetingRepository,
                                 final JoinedMoimFinder joinedMoimFinder,
                                 final ScheduleAppender scheduleAppender) {
        this.joinedMeetingRepository = joinedMeetingRepository;
        this.joinedMoimFinder = joinedMoimFinder;
        this.scheduleAppender = scheduleAppender;
    }

    @Transactional
    public void saveJoinedMeeting(final long moimId, final long meetingId,
                                  final String moimTitle, final MeetingJpaEntity meetingJpaEntity) {
        List<Long> memberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);

        for (long memberId : memberIds) {
            boolean alreadyJoinedMeeting = joinedMeetingRepository.alreadyJoinedMeeting(memberId, meetingId);

            if(!alreadyJoinedMeeting) {
                JoinedMeetingJpaEntity joinedMeetingJpaEntity =
                        JoinedMeetingJpaEntity.toEntity(meetingId, memberId, true);
                joinedMeetingRepository.save(joinedMeetingJpaEntity);
                ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.toEntity(memberId, moimTitle, meetingJpaEntity);
                scheduleAppender.createScheduleIfNotExist(scheduleJpaEntity);
            }
        }
    }
}
