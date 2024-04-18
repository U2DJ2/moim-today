package moim_today.persistence.repository.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MoimRepositoryImpl implements MoimRepository {

    private final MoimJpaRepository moimJpaRepository;

    public MoimRepositoryImpl(final MoimJpaRepository moimJpaRepository) {
        this.moimJpaRepository = moimJpaRepository;
    }

    @Override
    public void save(final MoimJpaEntity moimJpaEntity) {
        moimJpaRepository.save(moimJpaEntity);
    }
}
