package booki_today.persistence.entity.custom_filter.custom_filter;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "custom_filter")
@Entity
public class CustomFilterJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_filter_id")
    private long id;

    @Association
    private long assembleId;

    private String customFilterName;

    protected CustomFilterJpaEntity() {
    }

    @Builder
    private CustomFilterJpaEntity(final long assembleId, final String customFilterName) {
        this.assembleId = assembleId;
        this.customFilterName = customFilterName;
    }
}
