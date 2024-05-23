package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.meeting.enums.MeetingCategory;

import java.time.LocalDateTime;

@Builder
public record MeetingCreateResponse(
        long meetingId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime,

        String place,
        MeetingCategory meetingCategory
) {

    public static MeetingCreateResponse of(final long meetingId, final MeetingCreateRequest meetingCreateRequest) {
        return MeetingCreateResponse.builder()
                .meetingId(meetingId)
                .agenda(meetingCreateRequest.agenda())
                .startDateTime(meetingCreateRequest.startDateTime())
                .endDateTime(meetingCreateRequest.endDateTime())
                .place(meetingCreateRequest.place())
                .meetingCategory(meetingCreateRequest.meetingCategory())
                .build();
    }
}
