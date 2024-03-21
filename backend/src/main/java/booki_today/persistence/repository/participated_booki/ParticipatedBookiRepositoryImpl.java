package booki_today.persistence.repository.participated_booki;

import org.springframework.stereotype.Repository;

@Repository
public class ParticipatedBookiRepositoryImpl implements ParticipatedBookiRepository {

    private final ParticipatedBookiJpaRepository participatedBookiJpaRepository;

    public ParticipatedBookiRepositoryImpl(final ParticipatedBookiJpaRepository participatedBookiJpaRepository) {
        this.participatedBookiJpaRepository = participatedBookiJpaRepository;
    }
}
