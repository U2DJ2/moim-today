package moim_today.persistence.repository.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;

public interface JoinedMoimRepository {
    void deleteAllByMoimId(final long moimId);

    void save(final JoinedMoimJpaEntity joinedMoimJpaEntity);
}
