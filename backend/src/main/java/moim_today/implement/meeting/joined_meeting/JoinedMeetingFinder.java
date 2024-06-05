package moim_today.implement.meeting.joined_meeting;

import moim_today.dto.member.MemberSimpleResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class JoinedMeetingFinder {

    private final JoinedMeetingRepository joinedMeetingRepository;

    public JoinedMeetingFinder(final JoinedMeetingRepository joinedMeetingRepository) {
        this.joinedMeetingRepository = joinedMeetingRepository;
    }

    @Transactional(readOnly = true)
    public boolean findAttendanceStatus(final long memberId, final long meetingId) {
        JoinedMeetingJpaEntity joinedMeetingJpaEntity = joinedMeetingRepository.getByMemberIdAndMeetingId(memberId, meetingId);
        return joinedMeetingJpaEntity.isAttendance();
    }

    @Transactional(readOnly = true)
    public List<Long> findAllMemberId(final long meetingId) {
        return joinedMeetingRepository.findAllMemberIdByMeetingId(meetingId);
    }

    @Transactional(readOnly = true)
    public List<MemberSimpleResponse> findMembersJoinedMeeting(final long meetingId) {
        return joinedMeetingRepository.findMembersJoinedMeeting(meetingId);
    }

    @Transactional(readOnly = true)
    public List<Long> findAllByMemberId(final long memberId) {
        return joinedMeetingRepository.findAllByMemberId(memberId);
    }
}
