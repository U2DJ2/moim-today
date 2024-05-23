package moim_today.application.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.meeting.*;

import java.util.List;

public interface MeetingService {

    MeetingCreateResponse createMeeting(final long memberId, final MeetingCreateRequest meetingCreateRequest);

    List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final long memberId, final MeetingStatus meetingStatus);

    MeetingDetailResponse findDetailsById(final long meetingId);

    void updateMeeting(final long memberId, final MeetingUpdateRequest meetingUpdateRequest);

    void deleteMeeting(final long memberId, final long meetingId);
}
