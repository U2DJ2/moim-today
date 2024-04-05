package moim_today.persistence.repository.regular_moim.joined_regular_moim;

import org.springframework.stereotype.Repository;

@Repository
public class JoinedRegularMoimRepositoryImpl implements JoinedRegularMoimRepository {

    private final JoinedRegularMoimJpaRepository joinedRegularMoimJpaRepository;

    public JoinedRegularMoimRepositoryImpl(final JoinedRegularMoimJpaRepository joinedRegularMoimJpaRepository) {
        this.joinedRegularMoimJpaRepository = joinedRegularMoimJpaRepository;
    }
}
