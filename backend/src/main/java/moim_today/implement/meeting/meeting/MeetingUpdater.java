package moim_today.implement.meeting.meeting;

import moim_today.dto.meeting.meeting.MeetingUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_FORBIDDEN_ERROR;

@Implement
public class MeetingUpdater {

    private final MeetingRepository meetingRepository;

    public MeetingUpdater(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional
    public void updateMeeting(final long memberId, final MeetingUpdateRequest meetingUpdateRequest) {
        MeetingJpaEntity meetingJpaEntity = meetingRepository.getById(meetingUpdateRequest.meetingId());
        long hostId = meetingRepository.getHostIdByMeetingId(meetingUpdateRequest.meetingId());
        validateHostId(memberId, hostId);
        meetingJpaEntity.updateMeeting(meetingUpdateRequest.agenda(), meetingUpdateRequest.place());
    }

    private void validateHostId(final long memberId, final long hostId) {
        if (memberId != hostId) {
            throw new ForbiddenException(MEETING_FORBIDDEN_ERROR.message());
        }
    }
}
