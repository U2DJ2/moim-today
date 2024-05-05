package moim_today.persistence.repository.moim.moim_notice;

import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;

public interface MoimNoticeRepository {

    void save(final MoimNoticeJpaEntity moimNoticeJpaEntity);

    long count();
}
