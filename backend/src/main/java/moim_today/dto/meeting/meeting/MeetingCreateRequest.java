package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record MeetingCreateRequest(
        @Min(value = 0, message = MOIM_ID_MIN_ERROR) long moimId,
        @NotBlank(message = MEETING_AGENDA_BLANK_ERROR) String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = MEETING_START_DATETIME_NULL_ERROR)
        LocalDateTime startDateTime,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @NotNull(message = MEETING_END_DATETIME_NULL_ERROR)
        LocalDateTime endDateTime,

        @NotBlank(message = MEETING_PLACE_BLANK_ERROR) String place,
        @NotNull(message = MEETING_CATEGORY_NULL_ERROR) MeetingCategory meetingCategory
) {

    public MeetingJpaEntity toEntity() {
        return MeetingJpaEntity.builder()
                .moimId(moimId)
                .agenda(agenda)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .place(place)
                .build();
    }

    public MeetingJpaEntity toEntity(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        return MeetingJpaEntity.builder()
                .moimId(moimId)
                .agenda(agenda)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .place(place)
                .build();
    }
}
