package moim_today.persistence.entity.moim.moim_notice;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

@Getter
@Table(name = "moim_notice")
@Entity
public class MoimNoticeJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moim_notice_id")
    private long id;

    @Association
    private long moimId;

    private String title;

    private String contents;

    protected MoimNoticeJpaEntity() {
    }

    @Builder
    private MoimNoticeJpaEntity(final long moimId, final String title, final String contents) {
        this.moimId = moimId;
        this.title = title;
        this.contents = contents;
    }
}
