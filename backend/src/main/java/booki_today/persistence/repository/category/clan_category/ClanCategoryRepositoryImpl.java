package booki_today.persistence.repository.category.clan_category;

import org.springframework.stereotype.Repository;

@Repository
public class ClanCategoryRepositoryImpl implements ClanCategoryRepository {

    private final ClanCategoryJpaRepository clanCategoryJpaRepository;

    public ClanCategoryRepositoryImpl(final ClanCategoryJpaRepository clanCategoryJpaRepository) {
        this.clanCategoryJpaRepository = clanCategoryJpaRepository;
    }
}
