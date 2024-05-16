package moim_today.presentation.meeting.meeting_comment;

import moim_today.application.meeting.meeting_comment.MeetingCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/meetings/comments")
@RestController
public class MeetingCommentController {

    private final MeetingCommentService meetingCommentService;

    public MeetingCommentController(final MeetingCommentService meetingCommentService) {
        this.meetingCommentService = meetingCommentService;
    }
}
