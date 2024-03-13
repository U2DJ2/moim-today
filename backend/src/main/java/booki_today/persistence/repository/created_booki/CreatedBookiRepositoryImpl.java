package booki_today.persistence.repository.created_booki;

import org.springframework.stereotype.Repository;

@Repository
public class CreatedBookiRepositoryImpl implements CreatedBookiRepository {

    private final CreatedBookiJpaRepository createdBookiJpaRepository;

    public CreatedBookiRepositoryImpl(final CreatedBookiJpaRepository createdBookiJpaRepository) {
        this.createdBookiJpaRepository = createdBookiJpaRepository;
    }
}
