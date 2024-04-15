package moim_today.persistence.repository.moim.meeting;

import org.springframework.stereotype.Repository;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository {

    private final MeetingJpaRepository meetingJpaRepository;

    public MeetingRepositoryImpl(final MeetingJpaRepository meetingJpaRepository) {
        this.meetingJpaRepository = meetingJpaRepository;
    }
}
