package booki_today.persistence.repository.custom_filter.custom_filter_status;

import org.springframework.stereotype.Repository;

@Repository
public class CustomFilterStatusRepositoryImpl implements CustomFilterStatusRepository {

    private final CustomFilterStatusJpaRepository customFilterStatusJpaRepository;

    public CustomFilterStatusRepositoryImpl(final CustomFilterStatusJpaRepository customFilterStatusJpaRepository) {
        this.customFilterStatusJpaRepository = customFilterStatusJpaRepository;
    }
}
