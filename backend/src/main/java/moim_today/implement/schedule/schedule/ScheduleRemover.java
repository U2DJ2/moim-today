package moim_today.implement.schedule.schedule;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.persistence.repository.schedule.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class ScheduleRemover {

    private final ScheduleRepository scheduleRepository;

    public ScheduleRemover(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void deleteSchedule(final long memberId, final long scheduleId) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepository.getById(scheduleId);
        scheduleJpaEntity.validateMember(memberId);
        scheduleRepository.delete(scheduleJpaEntity);
    }

    @Transactional
    public void deleteAllByMeetingIdIn(final List<Long> meetingIds) {
        if (!meetingIds.isEmpty()) {
            scheduleRepository.deleteAllByMeetingIdIn(meetingIds);
        }
    }

    @Transactional
    public void deleteAllByMeetingId(final long meetingId) {
        scheduleRepository.deleteAllByMeetingId(meetingId);
    }

    @Transactional
    public void deleteAllByMemberInMeeting(final long memberId, final List<Long> meetingIds) {
        if(!meetingIds.isEmpty()){
            scheduleRepository.deleteAllByMemberInMeeting(memberId, meetingIds);
        }
    }

    @Transactional
    public void deleteByMemberIdAndMeetingId(final long memberId, final long meetingId) {
        scheduleRepository.deleteByMemberIdAndMeetingId(memberId, meetingId);
    }

    @Transactional
    public void deleteAllByMemberId(final long memberId) {
        scheduleRepository.deleteAllByMemberId(memberId);
    }
}
