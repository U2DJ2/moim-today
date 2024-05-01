package moim_today.persistence.repository.meeting.joined_meeting;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JoinedMeetingRepositoryImpl implements JoinedMeetingRepository {

    private final JoinedMeetingJpaRepository joinedMeetingJpaRepository;

    public JoinedMeetingRepositoryImpl(final JoinedMeetingJpaRepository joinedMeetingJpaRepository) {
        this.joinedMeetingJpaRepository = joinedMeetingJpaRepository;
    }

    @Override
    public void deleteAllByMeetingIdIn(final List<Long> meetingIds) {
        joinedMeetingJpaRepository.deleteAllByMeetingIdIn(meetingIds);
    }
}
