package moim_today.application.meeting;

import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.implement.meeting.meeting.MeetingAppender;
import org.springframework.stereotype.Service;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingAppender meetingAppender;

    public MeetingServiceImpl(final MeetingAppender meetingAppender) {
        this.meetingAppender = meetingAppender;
    }

    @Override
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        meetingAppender.createMeeting(meetingCreateRequest);
    }
}
