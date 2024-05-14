package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;

import java.time.LocalDateTime;

import static moim_today.global.constant.NumberConstant.SCHEDULE_MEETING_ID;
import static moim_today.global.constant.NumberConstant.SCHEDULE_MOIM_ID;

@Builder
public record ScheduleCreateRequest(
        String scheduleName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime
) {

    public ScheduleJpaEntity toEntity(final long memberId, final String colorHex) {
        return ScheduleJpaEntity.builder()
                .memberId(memberId)
                .moimId(SCHEDULE_MOIM_ID.value())
                .meetingId(SCHEDULE_MEETING_ID.value())
                .scheduleName(scheduleName)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .colorHex(colorHex)
                .build();
    }
}
