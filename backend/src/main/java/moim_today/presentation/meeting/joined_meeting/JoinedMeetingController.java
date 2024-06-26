package moim_today.presentation.meeting.joined_meeting;

import moim_today.application.meeting.joined_meeting.JoinedMeetingService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.meeting.meeting.MeetingAttendanceResponse;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/api")
@RestController
public class JoinedMeetingController {

    private final JoinedMeetingService joinedMeetingService;

    public JoinedMeetingController(final JoinedMeetingService joinedMeetingService) {
        this.joinedMeetingService = joinedMeetingService;
    }

    @GetMapping("/members/meetings/{meetingId}")
    public MeetingAttendanceResponse findAttendanceStatus(@Login final MemberSession memberSession,
                                                          @PathVariable final long meetingId) {
        boolean attendanceStatus = joinedMeetingService.findAttendanceStatus(memberSession.id(), meetingId);
        return MeetingAttendanceResponse.from(attendanceStatus);
    }

    @PostMapping("/members/meetings/{meetingId}/acceptance")
    public void acceptanceJoinMeeting(@Login final MemberSession memberSession,
                                      @PathVariable final long meetingId) {
        joinedMeetingService.acceptanceJoinMeeting(memberSession.id(), meetingId, LocalDateTime.now());
    }

    @PostMapping("/members/meetings/{meetingId}/refusal")
    public void refuseJoinMeeting(@Login final MemberSession memberSession,
                                  @PathVariable final long meetingId) {
        joinedMeetingService.refuseJoinMeeting(memberSession.id(), meetingId, LocalDateTime.now());
    }
}
