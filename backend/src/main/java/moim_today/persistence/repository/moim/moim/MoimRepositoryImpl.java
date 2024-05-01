package moim_today.persistence.repository.moim.moim;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.stereotype.Repository;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_NOT_FOUND_ERROR;

@Repository
public class MoimRepositoryImpl implements MoimRepository {

    private final MoimJpaRepository moimJpaRepository;

    public MoimRepositoryImpl(final MoimJpaRepository moimJpaRepository) {
        this.moimJpaRepository = moimJpaRepository;
    }

    @Override
    public MoimJpaEntity save(final MoimJpaEntity moimJpaEntity) {
        return moimJpaRepository.save(moimJpaEntity);
    }

    @Override
    public long count() {
        return moimJpaRepository.count();
    }

    @Override
    public MoimJpaEntity getById(final long moimId) {
        return moimJpaRepository.findById(moimId)
                .orElseThrow(() -> new NotFoundException(MOIM_NOT_FOUND_ERROR.message()));
    }

    @Override
    public void deleteById(final long moimId) {
        moimJpaRepository.deleteById(moimId);
    }
}
