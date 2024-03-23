package booki_today.persistence.repository.custom_filter.custom_filter;

import org.springframework.stereotype.Repository;

@Repository
public class CustomFilterRepositoryImpl implements CustomFilterRepository {

    private final CustomFilterJpaRepository customFilterJpaRepository;

    public CustomFilterRepositoryImpl(final CustomFilterJpaRepository customFilterJpaRepository) {
        this.customFilterJpaRepository = customFilterJpaRepository;
    }
}
