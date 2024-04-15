package moim_today.persistence.entity.moim.moim;

import moim_today.domain.moim.enums.MoimCategory;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "moim")
@Entity
public class MoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moim_id")
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
    private MoimCategory moimCategory;

    private int views;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    protected MoimJpaEntity() {
    }

    @Builder
    private MoimJpaEntity(final long universityId, final long memberId, final String title,
                         final String contents, final int capacity, final int currentCount,
                         final String imageUrl, final String password, final MoimCategory moimCategory,
                         final int views, final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        this.universityId = universityId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.imageUrl = imageUrl;
        this.password = password;
        this.moimCategory = moimCategory;
        this.views = views;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
