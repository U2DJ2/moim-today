package moim_today.application.meeting.meeting;

import moim_today.application.meeting.joined_meeting.JoinedMeetingService;
import moim_today.application.schedule.ScheduleService;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingManager meetingManager;
    private final MeetingFinder meetingFinder;
    private final MeetingRemover meetingRemover;
    private final ScheduleService scheduleService;
    private final JoinedMeetingService joinedMeetingService;

    public MeetingServiceImpl(final MeetingManager meetingManager, final MeetingFinder meetingFinder,
                              final MeetingRemover meetingRemover, final ScheduleService scheduleService, final JoinedMeetingService joinedMeetingService) {
        this.meetingManager = meetingManager;
        this.meetingFinder = meetingFinder;
        this.meetingRemover = meetingRemover;
        this.scheduleService = scheduleService;
        this.joinedMeetingService = joinedMeetingService;
    }

    @Override
    public MeetingCreateResponse createMeeting(final long memberId, final MeetingCreateRequest meetingCreateRequest) {
        return meetingManager.createMeeting(memberId, meetingCreateRequest, LocalDate.now());
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

    @Transactional
    @Override
    public void deleteMeeting(final long memberId, final long meetingId) {
        meetingRemover.deleteMeeting(meetingId, meetingId);
        scheduleService.deleteAllByMeetingId(meetingId);
        joinedMeetingService.deleteAllByMeetingId(meetingId);
    }
}
