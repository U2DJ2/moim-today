package moim_today.persistence.entity.schedule.schedule;

import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.error.ForbiddenException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_FORBIDDEN;

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

    private String color;

    protected ScheduleJpaEntity() {
    }

    @Builder
    private ScheduleJpaEntity(final long memberId, final long meetingId, final String scheduleName,
                              final DayOfWeek dayOfWeek, final LocalDateTime startDateTime,
                              final LocalDateTime endDateTime, final String color) {
        this.memberId = memberId;
        this.meetingId = meetingId;
        this.scheduleName = scheduleName;
        this.dayOfWeek = dayOfWeek;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.color = color;
    }

    public void updateSchedule(final ScheduleUpdateRequest scheduleUpdateRequest) {
        this.scheduleName = scheduleUpdateRequest.scheduleName();
        this.dayOfWeek = scheduleUpdateRequest.dayOfWeek();
        this.startDateTime = scheduleUpdateRequest.startDateTime();
        this.endDateTime = scheduleUpdateRequest.endDateTime();
    }

    public void validateMember(final long memberId) {
        if (this.memberId != memberId) {
            throw new ForbiddenException(SCHEDULE_FORBIDDEN.message());
        }
    }
}
