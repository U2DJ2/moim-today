package moim_today.persistence.entity.single_moim.single_moim;

import moim_today.domain.single_moim.enums.SingleMoimCategory;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "single_moim")
@Entity
public class SingleMoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "single_moim_id")
    private long id;

    @Association
    private long universityId;

    @Association
    private long memberId;

    private String title;

    private String contents;

    private int capacity;

    private int currentCount;

    @Enumerated(EnumType.STRING)
    private SingleMoimCategory singleMoimCategory;

    protected SingleMoimJpaEntity() {
    }

    @Builder
    private SingleMoimJpaEntity(final long universityId, final long memberId, final String title,
                                final String contents, final int capacity, final int currentCount,
                                final SingleMoimCategory singleMoimCategory) {
        this.universityId = universityId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.singleMoimCategory = singleMoimCategory;
    }
}
