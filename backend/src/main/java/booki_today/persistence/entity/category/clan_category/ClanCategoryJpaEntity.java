package booki_today.persistence.entity.category.clan_category;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "clan_category")
@Entity
public class ClanCategoryJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clan_category_id")
    private long id;

    @Association
    private long categoryId;

    @Association
    private long clanId;

    protected ClanCategoryJpaEntity() {
    }

    @Builder
    private ClanCategoryJpaEntity(final long categoryId, final long clanId) {
        this.categoryId = categoryId;
        this.clanId = clanId;
    }
}
