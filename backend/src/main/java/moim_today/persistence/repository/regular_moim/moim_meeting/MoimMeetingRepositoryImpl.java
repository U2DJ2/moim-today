package moim_today.persistence.repository.regular_moim.moim_meeting;

import org.springframework.stereotype.Repository;

@Repository
public class MoimMeetingRepositoryImpl implements MoimMeetingRepository {

    private final MoimMeetingJpaRepository moimMeetingJpaRepository;

    public MoimMeetingRepositoryImpl(final MoimMeetingJpaRepository moimMeetingJpaRepository) {
        this.moimMeetingJpaRepository = moimMeetingJpaRepository;
    }
}
