package booki_today.persistence.entity.single_moim.participated_single_moim;

import booki_today.global.annotation.Association;
import booki_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "participated_single_moim")
@Entity
public class ParticipatedSingleMoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_single_moim_id")
    private long id;

    @Association
    private long singleMoimId;

    @Association
    private long memberId;

    protected ParticipatedSingleMoimJpaEntity() {
    }

    @Builder
    private ParticipatedSingleMoimJpaEntity(final long singleMoimId, final long memberId) {
        this.singleMoimId = singleMoimId;
        this.memberId = memberId;
    }
}
