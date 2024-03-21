package booki_today.persistence.entity.custom_filter.custom_filter_status;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "custom_filter_status")
@Entity
public class CustomFilterStatusJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_filter_status_id")
    private long id;

    @Association
    private long customFilterId;

    @Association
    private long memberId;

    private boolean filterStatus;

    protected CustomFilterStatusJpaEntity() {
    }

    @Builder
    private CustomFilterStatusJpaEntity(final long customFilterId, final long memberId, final boolean filterStatus) {
        this.customFilterId = customFilterId;
        this.memberId = memberId;
        this.filterStatus = filterStatus;
    }
}
