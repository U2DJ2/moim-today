package booki_today.persistence.entity.assemble.participated_assemble;

import booki_today.domain.participated_booki.ParticipatedStatus;
import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "participated_assemble")
@Entity
public class ParticipatedAssembleJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_assemble_id")
    private long id;

    @Association
    private long memberId;

    @Association
    private long assembleId;

    @Enumerated(EnumType.STRING)
    private ParticipatedStatus participatedStatus;

    protected ParticipatedAssembleJpaEntity() {
    }

    @Builder
    private ParticipatedAssembleJpaEntity(final long memberId, final long assembleId,
                                          final ParticipatedStatus participatedStatus) {
        this.memberId = memberId;
        this.assembleId = assembleId;
        this.participatedStatus = participatedStatus;
    }
}
