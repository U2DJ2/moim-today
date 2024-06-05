package moim_today.dto.meeting.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record JoinedMeetingDao(
        long meetingId,
        String agenda,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime
) {

    @QueryProjection
    public JoinedMeetingDao {
    }
}
