package moim_today.application.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.MeetingSimpleDao;
import moim_today.dto.meeting.MeetingSimpleResponse;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.meeting.meeting.MeetingManager;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingManager meetingManager;
    private final MeetingFinder meetingFinder;

    public MeetingServiceImpl(final MeetingManager meetingManager, final MeetingFinder meetingFinder) {
        this.meetingManager = meetingManager;
        this.meetingFinder = meetingFinder;
    }

    @Override
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {
        meetingManager.createMeeting(meetingCreateRequest);
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
}
