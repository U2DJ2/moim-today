package moim_today.presentation.admin.moim;

import moim_today.application.admin.moim.AdminMoimService;
import moim_today.domain.university.AdminSession;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminMoimController {

    private final AdminMoimService adminMoimService;

    public AdminMoimController(final AdminMoimService adminMoimService) {
        this.adminMoimService = adminMoimService;
    }

    @GetMapping("/moims")
    public CollectionResponse<List<MoimSimpleResponse>> findAllByUniversityId(@Login final AdminSession adminSession) {
        List<MoimSimpleResponse> moimSimpleResponses = adminMoimService.findAllByUniversityId(adminSession.universityId());
        return CollectionResponse.from(moimSimpleResponses);
    }

    @DeleteMapping("/moims/{moimId}")
    public void deleteMoim(@Login final AdminSession adminSession, @PathVariable final long moimId) {
        adminMoimService.deleteMoim(adminSession.universityId(), moimId);
    }
}
