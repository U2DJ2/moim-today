package moim_today.dto.schedule;

import lombok.Builder;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static moim_today.global.constant.NumberConstant.SCHEDULE_MEETING_ID;

@Builder
public record ScheduleCreateRequest(
        String scheduleName,
        DayOfWeek dayOfWeek,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {

    public ScheduleJpaEntity toEntity(final long memberId) {
        return ScheduleJpaEntity.builder()
                .memberId(memberId)
                .meetingId(SCHEDULE_MEETING_ID.value())
                .scheduleName(scheduleName)
                .dayOfWeek(dayOfWeek)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
