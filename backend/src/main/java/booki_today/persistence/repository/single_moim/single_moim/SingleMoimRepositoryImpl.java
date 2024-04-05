package booki_today.persistence.repository.single_moim.single_moim;

import org.springframework.stereotype.Repository;

@Repository
public class SingleMoimRepositoryImpl implements SingleMoimRepository {

    private final SingleMoimJpaRepository singleMoimJpaRepository;

    public SingleMoimRepositoryImpl(final SingleMoimJpaRepository singleMoimJpaRepository) {
        this.singleMoimJpaRepository = singleMoimJpaRepository;
    }
}
