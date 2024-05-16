package moim_today.presentation.meeting.meeting_comment;

import moim_today.application.meeting.meeting_comment.MeetingCommentService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.dto.meeting.meeting_comment.MeetingCommentResponse;
import moim_today.dto.meeting.meeting_comment.MeetingCommentUpdateRequest;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/meeting-comments")
@RestController
public class MeetingCommentController {

    private final MeetingCommentService meetingCommentService;

    public MeetingCommentController(final MeetingCommentService meetingCommentService) {
        this.meetingCommentService = meetingCommentService;
    }

    @PostMapping
    public void createMeetingComment(@Login final MemberSession memberSession,
                                     @RequestBody final MeetingCommentCreateRequest meetingCommentCreateRequest) {
        meetingCommentService.createMeetingComment(memberSession.id(), meetingCommentCreateRequest);
    }

    @GetMapping("/{meetingId}")
    public CollectionResponse<List<MeetingCommentResponse>> findAllByMeetingId(@Login final MemberSession memberSession,
                                                                               @PathVariable final long meetingId) {
        return CollectionResponse.from(meetingCommentService.findAllByMeetingId(memberSession.id(), meetingId));
    }

    @PatchMapping
    public void updateMeetingComment(@Login final MemberSession memberSession,
                                     @RequestBody final MeetingCommentUpdateRequest meetingCommentUpdateRequest) {
        meetingCommentService.updateMeetingComment(memberSession.id(), meetingCommentUpdateRequest);
    }
}
