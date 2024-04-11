package booki_today.persistence.entity.clan.clan;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "clan")
@Entity
public class ClanJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clan_id")
    private long id;

    @Association
    private long memberId;

    private String clanName;

    protected ClanJpaEntity() {
    }

    @Builder
    private ClanJpaEntity(final long memberId, final String clanName) {
        this.memberId = memberId;
        this.clanName = clanName;
    }
}
