package booki_today.persistence.entity.regular_moim.regular_moim;

import booki_today.domain.regular_moim.enums.RegularMoimCategory;
import booki_today.global.annotation.Association;
import booki_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "regular_moim")
@Entity
public class RegularMoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regular_moim_id")
    private long id;

    @Association
    private long universityId;

    @Association
    private long memberId;

    private String title;

    private String contents;

    private int capacity;

    private int currentCount;

    private String imageUrl;

    private String password;

    @Enumerated
    private RegularMoimCategory regularMoimCategory;

    private int views;

    protected RegularMoimJpaEntity() {
    }

    @Builder
    private RegularMoimJpaEntity(final long universityId, final long memberId, final String title,
                                 final String contents, final int capacity, final int currentCount,
                                 final String imageUrl, final String password,
                                 final RegularMoimCategory regularMoimCategory, final int views) {
        this.universityId = universityId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.imageUrl = imageUrl;
        this.password = password;
        this.regularMoimCategory = regularMoimCategory;
        this.views = views;
    }
}
