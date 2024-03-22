package booki_today.persistence.repository.assemble.assemble;

import org.springframework.stereotype.Repository;

@Repository
public class AssembleRepositoryImpl implements AssembleRepository {

    private final AssembleJpaRepository assembleJpaRepository;

    public AssembleRepositoryImpl(final AssembleJpaRepository assembleJpaRepository) {
        this.assembleJpaRepository = assembleJpaRepository;
    }
}
