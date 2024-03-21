package booki_today.persistence.entity.clan.clan_member;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "clan_member")
@Entity
public class ClanMemberJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clan_member_id")
    private long id;

    @Association
    private long memberId;

    @Association
    private long clanId;

    protected ClanMemberJpaEntity() {
    }

    @Builder
    private ClanMemberJpaEntity(final long memberId, final long clanId) {
        this.memberId = memberId;
        this.clanId = clanId;
    }
}
