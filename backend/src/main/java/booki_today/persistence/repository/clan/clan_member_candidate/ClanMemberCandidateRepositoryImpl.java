package booki_today.persistence.repository.clan.clan_member_candidate;

import org.springframework.stereotype.Repository;

@Repository
public class ClanMemberCandidateRepositoryImpl implements ClanMemberCandidateRepository {

    private final ClanMemberCandidateJpaRepository clanMemberCandidateJpaRepository;

    public ClanMemberCandidateRepositoryImpl(final ClanMemberCandidateJpaRepository clanMemberCandidateJpaRepository) {
        this.clanMemberCandidateJpaRepository = clanMemberCandidateJpaRepository;
    }
}
