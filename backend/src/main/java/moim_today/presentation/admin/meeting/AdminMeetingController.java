package moim_today.presentation.admin.meeting;

import moim_today.application.admin.meeting.AdminMeetingService;
import moim_today.dto.admin.meeting.AdminMeetingResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminMeetingController {

    private final AdminMeetingService adminMeetingService;

    public AdminMeetingController(final AdminMeetingService adminMeetingService) {
        this.adminMeetingService = adminMeetingService;
    }

    // todo AdminSession 적용
    @GetMapping("/meetings/{moimId}")
    public List<AdminMeetingResponse> findAllByMoimId(final long universityId, @PathVariable final long moimId) {
        return adminMeetingService.findAllByMoimId(universityId, moimId);
    }

    // todo AdminSession 적용
    @DeleteMapping("/meetings/{meetingId}")
    public void deleteMeeting(final long universityId, @PathVariable final long meetingId) {
        adminMeetingService.deleteMeeting(universityId, meetingId);
    }
}
