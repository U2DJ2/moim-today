package booki_today.persistence.repository.regular_moim.moim_meeting;

import booki_today.persistence.repository.regular_moim.moim_schedule.MoimScheduleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MoimMeetingRepositoryImpl implements MoimMeetingRepository {

    private final MoimScheduleJpaRepository moimScheduleJpaRepository;

    public MoimMeetingRepositoryImpl(final MoimScheduleJpaRepository moimScheduleJpaRepository) {
        this.moimScheduleJpaRepository = moimScheduleJpaRepository;
    }
}
