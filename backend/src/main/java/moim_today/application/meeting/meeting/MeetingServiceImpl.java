package moim_today.application.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.meeting.*;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingComposition;
import moim_today.implement.meeting.meeting.*;
import moim_today.implement.schedule.schedule.ScheduleComposition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingComposition meetingComposition;
    private final ScheduleComposition scheduleComposition;
    private final JoinedMeetingComposition joinedMeetingComposition;

    public MeetingServiceImpl(final MeetingComposition meetingComposition,
                              final ScheduleComposition scheduleComposition,
                              final JoinedMeetingComposition joinedMeetingComposition) {
        this.meetingComposition = meetingComposition;
        this.scheduleComposition = scheduleComposition;
        this.joinedMeetingComposition = joinedMeetingComposition;
    }

    @Override
    public MeetingCreateResponse createMeeting(final long memberId, final MeetingCreateRequest meetingCreateRequest) {
        return meetingComposition.createMeeting(memberId, meetingCreateRequest, LocalDate.now());
    }

    @Override
    public List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final long memberId,
                                                       final MeetingStatus meetingStatus) {
        List<MeetingSimpleDao> meetingSimpleDaos =
                meetingComposition.findAllByMoimId(moimId, memberId, meetingStatus);
        return MeetingSimpleResponse.toResponses(meetingSimpleDaos);
    }

    @Override
    public MeetingDetailResponse findDetailsById(final long meetingId) {
        return meetingComposition.findDetailsById(meetingId);
    }

    @Override
    public void updateMeeting(final long memberId, final MeetingUpdateRequest meetingUpdateRequest) {
        meetingComposition.updateMeeting(memberId, meetingUpdateRequest);
    }

    @Transactional
    @Override
    public void deleteMeeting(final long memberId, final long meetingId) {
        meetingComposition.deleteMeeting(memberId, meetingId);
        scheduleComposition.deleteAllByMeetingId(meetingId);
        joinedMeetingComposition.deleteAllByMeetingId(meetingId);
    }
}
