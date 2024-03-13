package booki_today.persistence.entity.participated_booki;

import booki_today.domain.participated_booki.ParticipatedStatus;
import booki_today.global.annotation.Association;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "participated_booki")
@Entity
public class ParticipatedBookiJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_booki_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long createdBookiId;

    @Enumerated(EnumType.STRING)
    private ParticipatedStatus participatedStatus;

    protected ParticipatedBookiJpaEntity() {
    }

    @Builder
    private ParticipatedBookiJpaEntity(final Long memberId, final Long createdBookiId,
                                       final ParticipatedStatus participatedStatus) {
        this.memberId = memberId;
        this.createdBookiId = createdBookiId;
        this.participatedStatus = participatedStatus;
    }
}
