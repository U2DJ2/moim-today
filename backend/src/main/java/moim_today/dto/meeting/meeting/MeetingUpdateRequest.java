package moim_today.dto.meeting.meeting;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MeetingUpdateRequest(
        @Min(value = 1, message = MEETING_ID_MIN_ERROR) long meetingId,
        @NotBlank(message = AGENDA_BLANK_ERROR)  String agenda,
        @NotBlank(message = PLACE_BLANK_ERROR)  String place
) {
    private static final String MEETING_ID_MIN_ERROR = "잘못된 미팅 ID 값이 입력 되었습니다.";
    private static final String AGENDA_BLANK_ERROR = "미팅 의제는 공백일 수 없습니다.";
    private static final String PLACE_BLANK_ERROR = "미팅 장소는 공백일 수 없습니다.";

    public static MeetingUpdateRequest of(final long meetingId, final String agenda, final String place) {
        return MeetingUpdateRequest.builder()
                .meetingId(meetingId)
                .agenda(agenda)
                .place(place)
                .build();
    }
}
