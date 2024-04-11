package booki_today.persistence.repository.clan.clan_member;

import booki_today.persistence.entity.clan.clan_member.ClanMemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClanMemberJpaRepository extends JpaRepository<ClanMemberJpaEntity, Long> {
}
