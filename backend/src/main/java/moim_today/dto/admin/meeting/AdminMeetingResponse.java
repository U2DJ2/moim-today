package moim_today.dto.admin.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record AdminMeetingResponse(
        long meetingId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime,

        String place
) {

    @QueryProjection
    public AdminMeetingResponse {
    }
}
