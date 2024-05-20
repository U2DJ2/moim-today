package moim_today.persistence.entity.email_subscribe;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.base_entity.BaseTimeEntity;

@Getter
@Table(name = "email_subscribe")
@Entity
public class EmailSubscribeJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_subscribe_id")
    private long id;

    private boolean subscribeStatus;

    protected EmailSubscribeJpaEntity() {
    }

    @Builder
    private EmailSubscribeJpaEntity(final boolean subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }
}
