package moim_today.presentation.meeting.meeting;

import moim_today.application.meeting.meeting.MeetingService;
import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingDetailResponse;
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
    public CollectionResponse<List<MeetingSimpleResponse>> findAllByMoimId(
            @PathVariable final long moimId,
            @RequestParam final MeetingStatus meetingStatus) {
        List<MeetingSimpleResponse> meetingSimpleResponses = meetingService.findAllByMoimId(moimId, meetingStatus);
        return CollectionResponse.of(meetingSimpleResponses);
    }

    @GetMapping("/detail/{meetingId}")
    public MeetingDetailResponse findDetailsByMoimId(@PathVariable final long meetingId) {
        return meetingService.findDetailsById(meetingId);
    }
}
