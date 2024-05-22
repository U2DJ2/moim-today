package moim_today.dto.meeting.meeting;


public record MeetingUpdateRequest(
        long meetingId,
        String agenda,
        String place
) {
}
