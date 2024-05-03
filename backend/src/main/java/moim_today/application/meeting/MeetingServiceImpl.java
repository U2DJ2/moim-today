package moim_today.application.meeting;

import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.implement.meeting.meeting.MeetingManager;
import org.springframework.stereotype.Service;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingManager meetingManager;

    public MeetingServiceImpl(final MeetingManager meetingManager) {
        this.meetingManager = meetingManager;
    }

    @Override
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        meetingManager.createMeeting(meetingCreateRequest);
    }
}
