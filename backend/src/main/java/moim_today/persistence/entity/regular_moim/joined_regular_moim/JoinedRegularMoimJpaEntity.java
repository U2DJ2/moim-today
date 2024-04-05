package moim_today.persistence.entity.regular_moim.joined_regular_moim;

import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "joined_regular_moim")
@Entity
public class JoinedRegularMoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joined_regular_moim_id")
    private long id;

    @Association
    private long regularMoimId;

    @Association
    private long memberId;

    protected JoinedRegularMoimJpaEntity() {
    }

    public JoinedRegularMoimJpaEntity(final long regularMoimId, final long memberId) {
        this.regularMoimId = regularMoimId;
        this.memberId = memberId;
    }
}
