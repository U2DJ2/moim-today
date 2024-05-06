package moim_today.presentation.meeting;

import moim_today.application.meeting.MeetingService;
import moim_today.dto.meeting.MeetingCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/meetings")
@RestController
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(final MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public void createMeeting(@RequestBody final MeetingCreateRequest meetingCreateRequest) {
        meetingService.createMeeting(meetingCreateRequest);
    }
}
