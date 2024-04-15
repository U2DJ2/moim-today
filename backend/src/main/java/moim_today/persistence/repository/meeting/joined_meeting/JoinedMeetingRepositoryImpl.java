package moim_today.persistence.repository.meeting.joined_meeting;

import org.springframework.stereotype.Repository;

@Repository
public class JoinedMeetingRepositoryImpl implements JoinedMeetingRepository {

    private final JoinedMeetingJpaRepository joinedMeetingJpaRepository;

    public JoinedMeetingRepositoryImpl(final JoinedMeetingJpaRepository joinedMeetingJpaRepository) {
        this.joinedMeetingJpaRepository = joinedMeetingJpaRepository;
    }
}
