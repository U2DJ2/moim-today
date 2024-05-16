package moim_today.presentation.meeting.meeting_comment;

import moim_today.application.meeting.meeting_comment.MeetingCommentService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/meetings/comments")
@RestController
public class MeetingCommentController {

    private final MeetingCommentService meetingCommentService;

    public MeetingCommentController(final MeetingCommentService meetingCommentService) {
        this.meetingCommentService = meetingCommentService;
    }

    @PostMapping()
    public void createMeetingComment(@Login final MemberSession memberSession,
                                     @RequestBody final MeetingCommentCreateRequest meetingCommentCreateRequest) {
        meetingCommentService.createMeetingComment(memberSession.id(), meetingCommentCreateRequest);
    }
}
