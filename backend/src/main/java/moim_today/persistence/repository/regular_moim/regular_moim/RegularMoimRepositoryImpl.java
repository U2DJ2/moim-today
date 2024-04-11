package moim_today.persistence.repository.regular_moim.regular_moim;

import org.springframework.stereotype.Repository;

@Repository
public class RegularMoimRepositoryImpl implements RegularMoimRepository {

    private final RegularMoimJpaRepository regularMoimJpaRepository;

    public RegularMoimRepositoryImpl(final RegularMoimJpaRepository regularMoimJpaRepository) {
        this.regularMoimJpaRepository = regularMoimJpaRepository;
    }
}
