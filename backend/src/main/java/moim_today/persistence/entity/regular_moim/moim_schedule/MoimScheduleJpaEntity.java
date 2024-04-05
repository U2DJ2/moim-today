package moim_today.persistence.entity.regular_moim.moim_schedule;

import moim_today.domain.regular_moim.enums.DayOfWeek;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "moim_schedule")
@Entity
public class MoimScheduleJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moim_schedule_id")
    private long id;

    @Association
    private long regularMoimId;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    protected MoimScheduleJpaEntity() {
    }

    @Builder
    private MoimScheduleJpaEntity(final long regularMoimId, final DayOfWeek dayOfWeek,
                                  final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        this.regularMoimId = regularMoimId;
        this.dayOfWeek = dayOfWeek;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
