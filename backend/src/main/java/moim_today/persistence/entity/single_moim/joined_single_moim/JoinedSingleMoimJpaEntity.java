package moim_today.persistence.entity.single_moim.joined_single_moim;

import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "joined_single_moim")
@Entity
public class JoinedSingleMoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joined_single_moim_id")
    private long id;

    @Association
    private long singleMoimId;

    @Association
    private long memberId;

    protected JoinedSingleMoimJpaEntity() {
    }

    @Builder
    private JoinedSingleMoimJpaEntity(final long singleMoimId, final long memberId) {
        this.singleMoimId = singleMoimId;
        this.memberId = memberId;
    }
}
