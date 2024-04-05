package booki_today.persistence.repository.regular_moim.participated_regular_moim;

import org.springframework.stereotype.Repository;

@Repository
public class ParticipatedRegularMoimRepositoryImpl implements ParticipatedRegularMoimRepository {

    private final ParticipatedRegularMoimJpaRepository participatedRegularMoimJpaRepository;

    public ParticipatedRegularMoimRepositoryImpl(final ParticipatedRegularMoimJpaRepository participatedRegularMoimJpaRepository) {
        this.participatedRegularMoimJpaRepository = participatedRegularMoimJpaRepository;
    }
}
