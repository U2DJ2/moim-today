package moim_today.presentation.admin.meeting;

import moim_today.application.admin.meeting.AdminMeetingService;
import moim_today.domain.university.AdminSession;
import moim_today.dto.admin.meeting.AdminMeetingResponse;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminMeetingController {

    private final AdminMeetingService adminMeetingService;

    public AdminMeetingController(final AdminMeetingService adminMeetingService) {
        this.adminMeetingService = adminMeetingService;
    }

    @GetMapping("/meetings/{moimId}")
    public List<AdminMeetingResponse> findAllByMoimId(@Login final AdminSession adminSession,
                                                      @PathVariable final long moimId) {
        return adminMeetingService.findAllByMoimId(adminSession.universityId(), moimId);
    }

    @DeleteMapping("/meetings/{meetingId}")
    public void deleteMeeting(@Login final AdminSession adminSession,
                              @PathVariable final long meetingId) {
        adminMeetingService.deleteMeeting(adminSession.universityId(), meetingId);
    }
}
