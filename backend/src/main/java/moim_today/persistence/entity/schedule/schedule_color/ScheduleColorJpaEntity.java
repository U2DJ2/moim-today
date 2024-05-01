package moim_today.persistence.entity.schedule.schedule_color;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

@Getter
@Table(name = "schedule_color")
@Entity
public class ScheduleColorJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_color_id")
    private long id;

    @Association
    private long memberId;

    private int colorCount;

    protected ScheduleColorJpaEntity() {
    }

    @Builder
    private ScheduleColorJpaEntity(final long memberId, final int colorCount) {
        this.memberId = memberId;
        this.colorCount = colorCount;
    }
}
