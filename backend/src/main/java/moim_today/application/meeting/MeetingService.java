package moim_today.application.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingSimpleResponse;

import java.util.List;

public interface MeetingService {

    void createMeeting(final MeetingCreateRequest meetingCreateRequest);

    List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final MeetingStatus meetingStatus);
}
