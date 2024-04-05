package moim_today.persistence.repository.single_moim.joined_single_moim;

import org.springframework.stereotype.Repository;

@Repository
public class JoinedSingleMoimRepositoryImpl implements JoinedSingleMoimRepository {

    private final JoinedSingleMoimJpaRepository joinedSingleMoimJpaRepository;

    public JoinedSingleMoimRepositoryImpl(final JoinedSingleMoimJpaRepository joinedSingleMoimJpaRepository) {
        this.joinedSingleMoimJpaRepository = joinedSingleMoimJpaRepository;
    }
}
