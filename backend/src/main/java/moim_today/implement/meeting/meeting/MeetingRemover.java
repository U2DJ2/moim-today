package moim_today.implement.meeting.meeting;

import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_FORBIDDEN_ERROR;

@Implement
public class MeetingRemover {

    private final MeetingRepository meetingRepository;

    public MeetingRemover(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional
    public void deleteMeeting(final long memberId, final long meetingId) {
        MeetingJpaEntity meetingJpaEntity = meetingRepository.getById(meetingId);
        long hostId = meetingRepository.getHostIdByMeetingId(meetingId);
        validateHostId(memberId, hostId);

        meetingRepository.delete(meetingJpaEntity);
    }

    private void validateHostId(final long memberId, final long hostId) {
        if (memberId != hostId) {
            throw new ForbiddenException(MEETING_FORBIDDEN_ERROR.message());
        }
    }
}
