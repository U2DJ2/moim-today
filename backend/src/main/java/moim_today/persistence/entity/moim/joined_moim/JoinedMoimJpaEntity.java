package moim_today.persistence.entity.moim.joined_moim;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "joined_moim")
@Entity
public class JoinedMoimJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joined_moim_id")
    private long id;

    @Association
    private long moimId;

    @Association
    private long memberId;

    protected JoinedMoimJpaEntity() {
    }

    @Builder
    private JoinedMoimJpaEntity(final long moimId, final long memberId) {
        this.moimId = moimId;
        this.memberId = memberId;
    }

    public static JoinedMoimJpaEntity of(final long memberId, final long moimId) {
        return JoinedMoimJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .build();
    }

    public static List<Long> extractMemberIds(final List<JoinedMoimJpaEntity> joinedMoimJpaEntities) {
        List<Long> memberIds = new ArrayList<>();
        joinedMoimJpaEntities.forEach(e -> memberIds.add(e.getMemberId()));
        return memberIds;
    }
}
