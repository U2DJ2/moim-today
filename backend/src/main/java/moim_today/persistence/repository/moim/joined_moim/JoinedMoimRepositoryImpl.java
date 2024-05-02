package moim_today.persistence.repository.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JoinedMoimRepositoryImpl implements JoinedMoimRepository {

    private final JoinedMoimJpaRepository joinedMoimJpaRepository;

    public JoinedMoimRepositoryImpl(final JoinedMoimJpaRepository joinedMoimJpaRepository) {
        this.joinedMoimJpaRepository = joinedMoimJpaRepository;
    }

    @Override
    public void deleteAllByMoimId(final long moimId) {
        joinedMoimJpaRepository.deleteAllByMoimId(moimId);
    }

    @Override
    public void save(final JoinedMoimJpaEntity joinedMoimJpaEntity) {
        joinedMoimJpaRepository.save(joinedMoimJpaEntity);
    }

    @Override
    public long count() {
        return joinedMoimJpaRepository.count();
    }
}
