package moim_today.persistence.entity.schedule;

import moim_today.domain.regular_moim.enums.DayOfWeek;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "schedule")
@Entity
public class ScheduleJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private long id;

    @Association
    private long memberId;

    @Association
    private long meetingId;

    private String scheduleName;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    protected ScheduleJpaEntity() {
    }

    @Builder
    private ScheduleJpaEntity(final long memberId, final long meetingId, final String scheduleName,
                             final DayOfWeek dayOfWeek, final LocalDateTime startDateTime,
                             final LocalDateTime endDateTime) {
        this.memberId = memberId;
        this.meetingId = meetingId;
        this.scheduleName = scheduleName;
        this.dayOfWeek = dayOfWeek;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
