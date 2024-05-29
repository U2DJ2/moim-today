package moim_today.dto.meeting.meeting;

public record MeetingAttendanceResponse(
        boolean attendanceStatus
) {

    public static MeetingAttendanceResponse from(final boolean attendanceStatus) {
        return new MeetingAttendanceResponse(attendanceStatus);
    }
}
