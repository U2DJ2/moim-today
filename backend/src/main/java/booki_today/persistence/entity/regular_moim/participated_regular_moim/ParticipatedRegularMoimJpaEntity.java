package booki_today.persistence.entity.regular_moim.participated_regular_moim;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "participated_regular_moim")
@Entity
public class ParticipatedRegularMoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_regular_moim_id")
    private long id;

    @Association
    private long regularMoimId;

    @Association
    private long memberId;

    protected ParticipatedRegularMoimJpaEntity() {
    }

    public ParticipatedRegularMoimJpaEntity(final long regularMoimId, final long memberId) {
        this.regularMoimId = regularMoimId;
        this.memberId = memberId;
    }
}
