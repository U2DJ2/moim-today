package moim_today.application.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.MeetingSimpleResponse;

import java.util.List;

public interface MeetingService {

    void createMeeting(final MeetingCreateRequest meetingCreateRequest);

    List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final long memberId, final MeetingStatus meetingStatus);

    MeetingDetailResponse findDetailsById(final long meetingId);

    void deleteMeeting(final long memberId, final long meetingId);
}
