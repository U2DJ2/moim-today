package moim_today.dto.meeting.meeting;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record MeetingUpdateRequest(
        @Min(value = 0, message = MEETING_ID_MIN_ERROR) long meetingId,
        @NotBlank(message = MEETING_AGENDA_BLANK_ERROR)  String agenda,
        @NotBlank(message = MEETING_PLACE_BLANK_ERROR)  String place
) {

    public static MeetingUpdateRequest of(final long meetingId, final String agenda, final String place) {
        return MeetingUpdateRequest.builder()
                .meetingId(meetingId)
                .agenda(agenda)
                .place(place)
                .build();
    }
}
