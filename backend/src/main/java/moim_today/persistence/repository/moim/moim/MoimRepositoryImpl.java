package moim_today.persistence.repository.moim.moim;

import org.springframework.stereotype.Repository;

@Repository
public class MoimRepositoryImpl implements MoimRepository {

    private final MoimJpaRepository moimJpaRepository;

    public MoimRepositoryImpl(final MoimJpaRepository moimJpaRepository) {
        this.moimJpaRepository = moimJpaRepository;
    }
}
