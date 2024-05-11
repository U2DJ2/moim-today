package moim_today.presentation.meeting.joined_meeting;

import moim_today.application.meeting.joined_meeting.JoinedMeetingService;
import moim_today.domain.member.MemberSession;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class JoinedMeetingController {

    private final JoinedMeetingService joinedMeetingService;

    public JoinedMeetingController(final JoinedMeetingService joinedMeetingService) {
        this.joinedMeetingService = joinedMeetingService;
    }

    @PostMapping("/members/meetings/{meetingId}/refusal")
    public void refuseJoinMeeting(@Login final MemberSession memberSession,
                                  @PathVariable final long meetingId) {
        joinedMeetingService.refuseJoinMeeting(memberSession.id(), meetingId);
    }
}
