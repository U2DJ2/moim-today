package moim_today.domain.meeting;

import moim_today.domain.meeting.enums.MeetingCategory;

import java.time.LocalDateTime;

public record Meeting(
        long moimId,
        String agenda,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String place,
        MeetingCategory meetingCategory
) {
}
