package booki_today.persistence.repository.single_moim.participated_single_moim;

import org.springframework.stereotype.Repository;

@Repository
public class ParticipatedSingleMoimRepositoryImpl implements ParticipatedSingleMoimRepository {

    private final ParticipatedSingleMoimJpaRepository participatedSingleMoimJpaRepository;

    public ParticipatedSingleMoimRepositoryImpl(final ParticipatedSingleMoimJpaRepository participatedSingleMoimJpaRepository) {
        this.participatedSingleMoimJpaRepository = participatedSingleMoimJpaRepository;
    }
}
