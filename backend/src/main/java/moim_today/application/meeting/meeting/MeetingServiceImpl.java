package moim_today.application.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.*;
import moim_today.dto.meeting.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.meeting.MeetingSimpleDao;
import moim_today.dto.meeting.meeting.MeetingSimpleResponse;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.meeting.meeting.MeetingManager;
import moim_today.implement.meeting.meeting.MeetingRemover;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingManager meetingManager;
    private final MeetingFinder meetingFinder;
    private final MeetingRemover meetingRemover;

    public MeetingServiceImpl(final MeetingManager meetingManager, final MeetingFinder meetingFinder,
                              final MeetingRemover meetingRemover) {
        this.meetingManager = meetingManager;
        this.meetingFinder = meetingFinder;
        this.meetingRemover = meetingRemover;
    }

    @Override
    public MeetingCreateResponse createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        return meetingManager.createMeeting(meetingCreateRequest, LocalDate.now());
    }

    @Override
    public List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final long memberId,
                                                       final MeetingStatus meetingStatus) {
        List<MeetingSimpleDao> meetingSimpleDaos =
                meetingFinder.findAllByMoimId(moimId, memberId, meetingStatus, LocalDateTime.now());
        return MeetingSimpleResponse.toResponses(meetingSimpleDaos);
    }

    @Override
    public MeetingDetailResponse findDetailsById(final long meetingId) {
        return meetingFinder.findDetailsById(meetingId);
    }

    @Override
    public void deleteMeeting(final long memberId, final long meetingId) {
        meetingRemover.deleteMeeting(meetingId, meetingId);
    }
}
