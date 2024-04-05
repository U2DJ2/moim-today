package moim_today.persistence.repository.regular_moim.moim_schedule;

import org.springframework.stereotype.Repository;

@Repository
public class MoimScheduleRepositoryImpl implements MoimScheduleRepository {

    private final MoimScheduleJpaRepository moimScheduleJpaRepository;

    public MoimScheduleRepositoryImpl(final MoimScheduleJpaRepository moimScheduleJpaRepository) {
        this.moimScheduleJpaRepository = moimScheduleJpaRepository;
    }
}
