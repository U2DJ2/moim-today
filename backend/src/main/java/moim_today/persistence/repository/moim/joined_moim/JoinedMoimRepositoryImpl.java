package moim_today.persistence.repository.moim.joined_moim;

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
}
