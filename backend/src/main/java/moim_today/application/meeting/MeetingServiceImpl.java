package moim_today.application.meeting;

import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingSimpleResponse;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.meeting.meeting.MeetingManager;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import org.springframework.stereotype.Service;

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
    public List<MeetingSimpleResponse> findAllByMoimId(final long moimId) {
        List<MeetingJpaEntity> meetingJpaEntities = meetingFinder.findAllByMoimId(moimId);
        return MeetingSimpleResponse.toResponses(meetingJpaEntities);
    }
}
