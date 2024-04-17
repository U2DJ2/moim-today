package moim_today.domain.schedule;

import lombok.Builder;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;


@Builder
public record Schedule(
        String scheduleName,
        DayOfWeek dayOfWeek,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    public static Schedule toDomain(final String scheduleName, final DayOfWeek dayOfWeek,
                                    final LocalDateTime startDateTime,
                                    final LocalDateTime endDateTime) {
        return Schedule.builder()
                .scheduleName(scheduleName)
                .dayOfWeek(dayOfWeek)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }

    public ScheduleJpaEntity toEntity(final long memberId, final long meetingId) {
        return ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(meetingId)
                .scheduleName(scheduleName)
                .dayOfWeek(dayOfWeek)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
