package moim_today.dto.meeting.meeting_comment;


import java.util.List;

public record MeetingCommentCollectionResponse (
        int count,
        List<MeetingCommentResponse> meetingCommentResponses
) {

    public static MeetingCommentCollectionResponse from(final List<MeetingCommentResponse> meetingCommentResponses) {
        return new MeetingCommentCollectionResponse(meetingCommentResponses.size(), meetingCommentResponses);
    }
}
