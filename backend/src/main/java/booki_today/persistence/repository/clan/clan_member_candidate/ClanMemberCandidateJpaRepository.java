package booki_today.persistence.repository.clan.clan_member_candidate;

import booki_today.persistence.entity.clan.clan_member_candidate.ClanMemberCandidateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClanMemberCandidateJpaRepository extends JpaRepository<ClanMemberCandidateJpaEntity, Long> {
}
