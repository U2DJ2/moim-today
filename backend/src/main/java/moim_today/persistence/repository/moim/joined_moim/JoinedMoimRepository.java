package moim_today.persistence.repository.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;

import java.util.List;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;

public interface JoinedMoimRepository {

    List<JoinedMoimJpaEntity> findMembersByMoimId(final long moimId);
    void deleteAllByMoimId(final long moimId);

    void save(final JoinedMoimJpaEntity joinedMoimJpaEntity);

    long count();
}
