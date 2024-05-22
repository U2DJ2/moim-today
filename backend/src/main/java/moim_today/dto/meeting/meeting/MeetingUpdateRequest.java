package moim_today.dto.meeting.meeting;

import lombok.Builder;

@Builder
public record MeetingUpdateRequest(
        long meetingId,
        String agenda,
        String place
) {

    public static MeetingUpdateRequest of(final long meetingId, final String agenda, final String place) {
        return MeetingUpdateRequest.builder()
                .meetingId(meetingId)
                .agenda(agenda)
                .place(place)
                .build();
    }
}
