package moim_today.persistence.entity.schedule.schedule_color;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

import static moim_today.global.constant.NumberConstant.SCHEDULE_COLOR_START_COUNT;

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

    public static ScheduleColorJpaEntity toEntity(final long memberId, final int colorCount) {
        return ScheduleColorJpaEntity.builder()
                .memberId(memberId)
                .colorCount(colorCount)
                .build();
    }

    public void updateColorCount(final int colorCount) {
        this.colorCount = colorCount;
    }
}
