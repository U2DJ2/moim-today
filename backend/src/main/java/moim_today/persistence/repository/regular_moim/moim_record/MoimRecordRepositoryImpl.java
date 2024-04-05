package moim_today.persistence.repository.regular_moim.moim_record;

import org.springframework.stereotype.Repository;

@Repository
public class MoimRecordRepositoryImpl implements MoimRecordRepository {

    private final MoimRecordJpaRepository moimRecordJpaRepository;

    public MoimRecordRepositoryImpl(final MoimRecordJpaRepository moimRecordJpaRepository) {
        this.moimRecordJpaRepository = moimRecordJpaRepository;
    }
}
