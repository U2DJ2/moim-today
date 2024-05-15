package moim_today.dto.mail;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record UpcomingMeetingNoticeResponse(
        long moimId,
        long joinedMeetingId,
        String email,
        String agenda,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String place,
        boolean attendance
) {

    @QueryProjection
    public UpcomingMeetingNoticeResponse {
    }
}
