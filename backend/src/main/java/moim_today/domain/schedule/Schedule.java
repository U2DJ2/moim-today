package moim_today.domain.schedule;

import lombok.Builder;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;

import java.time.LocalDateTime;


@Builder
public record Schedule(
        String scheduleName,
        String colorHex,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    public static Schedule toDomain(final String scheduleName, final String colorHex,
                                    final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return Schedule.builder()
                .scheduleName(scheduleName)
                .colorHex(colorHex)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }

    public ScheduleJpaEntity toEntity(final long memberId, final long meetingId) {
        return ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(scheduleName)
                .colorHex(colorHex)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }

    public boolean exists(final ScheduleJpaEntity scheduleJpaEntity) {
        return scheduleJpaEntity.getEndDateTime().isAfter(startDateTime) &&
                scheduleJpaEntity.getStartDateTime().isBefore(endDateTime);
    }
}
