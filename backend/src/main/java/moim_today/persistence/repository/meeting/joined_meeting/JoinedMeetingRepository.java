package moim_today.persistence.repository.meeting.joined_meeting;

import moim_today.dto.member.MemberSimpleResponse;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;

import java.util.List;

public interface JoinedMeetingRepository {

    void deleteAllByMeetingIdIn(final List<Long> meetingIds);

    JoinedMeetingJpaEntity save(final JoinedMeetingJpaEntity joinedMeetingJpaEntity);

    void saveAll(final List<JoinedMeetingJpaEntity> joinedMeetingJpaEntities);

    List<Long> findAllMemberIdByMeetingId(final long meetingId);

    JoinedMeetingJpaEntity findByMemberIdAndMeetingId(final long memberId, final long meetingId);

    List<JoinedMeetingJpaEntity> findAll();

    long count();

    void deleteAllByMemberInMeeting(final long memberId, final List<Long> meetingIds);

    JoinedMeetingJpaEntity getById(final long joinedMeetingId);

    List<MemberSimpleResponse> findMembersJoinedMeeting(final long meetingId);
}
