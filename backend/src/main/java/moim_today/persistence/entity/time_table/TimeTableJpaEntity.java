package moim_today.persistence.entity.time_table;

import moim_today.domain.regular_moim.enums.DayOfWeek;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "time_table")
@Entity
public class TimeTableJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_table_id")
    private long id;

    @Association
    private long memberId;

    private String scheduleName;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    protected TimeTableJpaEntity() {
    }

    @Builder
    private TimeTableJpaEntity(final long memberId, final String scheduleName, final DayOfWeek dayOfWeek,
                               final LocalDateTime startTime, final LocalDateTime endTime) {
        this.memberId = memberId;
        this.scheduleName = scheduleName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
