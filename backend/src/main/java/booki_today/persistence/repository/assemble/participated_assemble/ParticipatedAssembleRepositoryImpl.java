package booki_today.persistence.repository.assemble.participated_assemble;

import org.springframework.stereotype.Repository;

@Repository
public class ParticipatedAssembleRepositoryImpl implements ParticipatedAssembleRepository {

    private final ParticipatedAssembleJpaRepository participatedAssembleJpaRepository;

    public ParticipatedAssembleRepositoryImpl(final ParticipatedAssembleJpaRepository participatedAssembleJpaRepository) {
        this.participatedAssembleJpaRepository = participatedAssembleJpaRepository;
    }
}
