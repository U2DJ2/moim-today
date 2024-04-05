package booki_today.persistence.repository.regular_moim.moim_record;

import booki_today.persistence.repository.regular_moim.moim_schedule.MoimScheduleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MoimRecordRepositoryImpl implements MoimRecordRepository {

    private final MoimScheduleJpaRepository moimScheduleJpaRepository;

    public MoimRecordRepositoryImpl(final MoimScheduleJpaRepository moimScheduleJpaRepository) {
        this.moimScheduleJpaRepository = moimScheduleJpaRepository;
    }
}
