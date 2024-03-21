package booki_today.persistence.repository.clan.clan_member;

import org.springframework.stereotype.Repository;

@Repository
public class ClanMemberRepositoryImpl implements ClanMemberRepository {

    private final ClanMemberJpaRepository clanMemberJpaRepository;

    public ClanMemberRepositoryImpl(final ClanMemberJpaRepository clanMemberJpaRepository) {
        this.clanMemberJpaRepository = clanMemberJpaRepository;
    }
}
