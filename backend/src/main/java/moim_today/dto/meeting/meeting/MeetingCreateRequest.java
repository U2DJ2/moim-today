package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDateTime;

@Builder
public record MeetingCreateRequest(
        @Min(value = 1, message = MOIM_ID_MIN_ERROR) long moimId,
        @NotBlank(message = AGENDA_BLANK_ERROR) String agenda,

        @NotNull(message = START_DATETIME_NULL_ERROR)
        @Future(message = START_DATETIME_FUTURE_ERROR)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,

        @NotNull(message = END_DATETIME_NULL_ERROR)
        @Future(message = END_DATETIME_FUTURE_ERROR)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime,

        @NotBlank(message = PLACE_BLANK_ERROR) String place,
        @NotNull(message = MEETING_CATEGORY_NULL_ERROR) MeetingCategory meetingCategory
) {
    private static final String MOIM_ID_MIN_ERROR = "잘못된 모임 ID 값이 입력 되었습니다.";
    private static final String AGENDA_BLANK_ERROR = "미팅 의제는 공백일 수 없습니다.";
    private static final String START_DATETIME_NULL_ERROR = "시작 날짜와 시간은 필수 입력 항목입니다.";
    private static final String START_DATETIME_FUTURE_ERROR = "시작 날짜와 시간은 현재 시간 이후여야 합니다.";
    private static final String END_DATETIME_NULL_ERROR = "종료 날짜와 시간은 필수 입력 항목입니다.";
    private static final String END_DATETIME_FUTURE_ERROR = "종료 날짜와 시간은 현재 시간 이후여야 합니다.";
    private static final String PLACE_BLANK_ERROR = "미팅 장소는 공백일 수 없습니다.";
    private static final String MEETING_CATEGORY_NULL_ERROR = "카테고리는 필수 입력 항목입니다.";

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
