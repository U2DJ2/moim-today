package moim_today.persistence.repository.moim.moim_notice;

import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MoimNoticeRepositoryImpl implements MoimNoticeRepository {

    private final MoimNoticeJpaRepository moimNoticeJpaRepository;

    public MoimNoticeRepositoryImpl(final MoimNoticeJpaRepository moimNoticeJpaRepository) {
        this.moimNoticeJpaRepository = moimNoticeJpaRepository;
    }

    @Override
    public void save(final MoimNoticeJpaEntity moimNoticeJpaEntity) {
        moimNoticeJpaRepository.save(moimNoticeJpaEntity);
    }

    @Override
    public long count() {
        return moimNoticeJpaRepository.count();
    }
}
