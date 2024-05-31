package moim_today.presentation.admin.meeting;

import moim_today.application.admin.meeting.AdminMeetingService;
import moim_today.dto.admin.meeting.AdminMeetingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminMeetingController {

    private final AdminMeetingService adminMeetingService;

    public AdminMeetingController(final AdminMeetingService adminMeetingService) {
        this.adminMeetingService = adminMeetingService;
    }

    @GetMapping("/meetings/{moimId}")
    public List<AdminMeetingResponse> findAllByUniversityId(@PathVariable final long moimId) {
        return adminMeetingService.findAll(moimId);
    }
}
