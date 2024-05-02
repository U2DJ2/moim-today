package moim_today.application.meeting;

import moim_today.dto.meeting.MeetingCreateRequest;

public interface MeetingService {

    void createMeeting(final MeetingCreateRequest meetingCreateRequest);
}
