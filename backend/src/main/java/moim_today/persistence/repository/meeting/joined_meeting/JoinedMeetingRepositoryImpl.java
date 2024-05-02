package moim_today.persistence.repository.meeting.joined_meeting;

import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
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

    @Override
    public void save(final JoinedMeetingJpaEntity joinedMeetingJpaEntity) {
        joinedMeetingJpaRepository.save(joinedMeetingJpaEntity);
    }

    @Override
    public long count() {
        return joinedMeetingJpaRepository.count();
    }
}
