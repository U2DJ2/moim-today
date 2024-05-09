package moim_today.presentation.meeting;

import moim_today.application.meeting.MeetingService;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingSimpleResponse;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/{moimId}")
    public CollectionResponse<List<MeetingSimpleResponse>> findMeetingsByMoimId(@PathVariable final long moimId) {
        List<MeetingSimpleResponse> meetingSimpleResponses = meetingService.findAllByMoimId(moimId);
        return CollectionResponse.of(meetingSimpleResponses);
    }
}
