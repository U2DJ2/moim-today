package booki_today.persistence.entity.clan.clan_member_candidate;


import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "clan_member_candidate")
@Entity
public class ClanMemberCandidateJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "clan_member_candidate")
    private long id;

    @Association
    private long clanId;

    @Association
    private long memberId;

    protected ClanMemberCandidateJpaEntity() {
    }

    @Builder
    private ClanMemberCandidateJpaEntity(final long clanId, final long memberId) {
        this.clanId = clanId;
        this.memberId = memberId;
    }
}
