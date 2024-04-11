package booki_today.persistence.repository.clan.clan;

import org.springframework.stereotype.Repository;

@Repository
public class ClanRepositoryImpl implements ClanRepository {

    private final ClanJpaRepository clanJpaRepository;

    public ClanRepositoryImpl(final ClanJpaRepository clanJpaRepository) {
        this.clanJpaRepository = clanJpaRepository;
    }
}
