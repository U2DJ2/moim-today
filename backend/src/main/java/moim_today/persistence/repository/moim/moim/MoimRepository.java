package moim_today.persistence.repository.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

public interface MoimRepository {

    MoimJpaEntity save(MoimJpaEntity moimJpaEntity);

    long count();
}
