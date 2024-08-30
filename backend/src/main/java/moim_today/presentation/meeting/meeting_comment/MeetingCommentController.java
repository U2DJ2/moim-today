package moim_today.presentation.meeting.meeting_comment;

import jakarta.validation.Valid;
import moim_today.application.meeting.meeting_comment.MeetingCommentService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.meeting.meeting_comment.*;
import moim_today.global.annotation.Login;
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
                                     @RequestBody @Valid final MeetingCommentCreateRequest meetingCommentCreateRequest) {
        meetingCommentService.createMeetingComment(memberSession.id(), meetingCommentCreateRequest);
    }

    @GetMapping("/{meetingId}")
    public MeetingCommentCollectionResponse findAllByMeetingId(@Login final MemberSession memberSession,
                                                               @PathVariable final long meetingId) {
        List<MeetingCommentResponse> meetingCommentResponses =
                meetingCommentService.findAllByMeetingId(memberSession.id(), meetingId);

        return MeetingCommentCollectionResponse.from(meetingCommentResponses);
    }

    @PatchMapping
    public void updateMeetingComment(@Login final MemberSession memberSession,
                                     @RequestBody @Valid final MeetingCommentUpdateRequest meetingCommentUpdateRequest) {
        meetingCommentService.updateMeetingComment(memberSession.id(), meetingCommentUpdateRequest);
    }

    @DeleteMapping
    public void deleteMeetingComment(@Login final MemberSession memberSession,
                                     @RequestBody @Valid final MeetingCommentDeleteRequest deleteMeetingCommentRequest) {
        meetingCommentService.deleteMeetingComment(memberSession.id(), deleteMeetingCommentRequest.meetingCommentId());
    }
}
