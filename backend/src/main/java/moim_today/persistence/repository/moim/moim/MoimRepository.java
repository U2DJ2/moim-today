package moim_today.persistence.repository.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

public interface MoimRepository {

    MoimJpaEntity save(final MoimJpaEntity moimJpaEntity);

    long count();

    MoimJpaEntity getById(final long moimId);

    void deleteById(final long moimId);
}
