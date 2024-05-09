package moim_today.fake_class.meeting;

import moim_today.application.meeting.MeetingService;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingSimpleResponse;

import java.util.List;

public class FakeMeetingService implements MeetingService {

    @Override
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {

    }

    @Override
    public List<MeetingSimpleResponse> findAllByMoimId(final long moimId) {
        return List.of();
    }
}
