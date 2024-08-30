package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;

import java.time.LocalDateTime;

import static moim_today.global.constant.NumberConstant.SCHEDULE_MEETING_ID;
import static moim_today.global.constant.NumberConstant.SCHEDULE_MOIM_ID;
import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record ScheduleCreateRequest(
        @NotBlank(message = SCHEDULE_NAME_BLANK_ERROR) String scheduleName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = SCHEDULE_START_DATE_TIME_NULL_ERROR) LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = SCHEDULE_END_DATE_TIME_NULL_ERROR) LocalDateTime endDateTime
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
