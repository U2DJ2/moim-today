package moim_today.implement.meeting.meeting;

import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.MeetingSimpleDao;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingFinder;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.repository.meeting.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Implement
public class MeetingFinder {

    private final MeetingRepository meetingRepository;
    private final JoinedMeetingFinder joinedMeetingFinder;

    public MeetingFinder(final MeetingRepository meetingRepository,
                         final JoinedMeetingFinder joinedMeetingFinder) {
        this.meetingRepository = meetingRepository;
        this.joinedMeetingFinder = joinedMeetingFinder;
    }

    @Transactional(readOnly = true)
    public List<Long> findMeetingIdsByMoimId(final long moimId) {
        return meetingRepository.findMeetingIdsByMoimId(moimId);
    }

    @Transactional(readOnly = true)
    public List<MeetingSimpleDao> findAllByMoimId(final long moimId, final long memberId,
                                                  final MeetingStatus meetingStatus,
                                                  final LocalDateTime currentDateTime) {
        if (meetingStatus.equals(MeetingStatus.ALL)) {
            return meetingRepository.findAllByMoimId(moimId, memberId, currentDateTime);
        } else if(meetingStatus.equals(MeetingStatus.PAST)) {
            return meetingRepository.findAllPastByMoimId(moimId, memberId, currentDateTime);
        }

        return meetingRepository.findAllUpcomingByMoimId(moimId, memberId, currentDateTime);
    }

    @Transactional(readOnly = true)
    public MeetingDetailResponse findDetailsById(final long meetingId) {
        MeetingJpaEntity meetingJpaEntity = meetingRepository.getById(meetingId);
        List<MemberSimpleResponse> memberSimpleResponses = joinedMeetingFinder.findMembersJoinedMeeting(meetingId);
        return MeetingDetailResponse.toResponse(meetingJpaEntity, memberSimpleResponses);
    }
}
