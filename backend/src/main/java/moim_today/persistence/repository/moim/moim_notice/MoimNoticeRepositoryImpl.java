package moim_today.persistence.repository.moim.moim_notice;

import org.springframework.stereotype.Repository;

@Repository
public class MoimNoticeRepositoryImpl implements MoimNoticeRepository {

    private final MoimNoticeJpaRepository moimNoticeJpaRepository;

    public MoimNoticeRepositoryImpl(final MoimNoticeJpaRepository moimNoticeJpaRepository) {
        this.moimNoticeJpaRepository = moimNoticeJpaRepository;
    }
}
