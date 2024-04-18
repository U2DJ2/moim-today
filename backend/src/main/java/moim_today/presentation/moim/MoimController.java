package moim_today.presentation.moim;

import moim_today.application.moim.MoimService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/moims")
@RestController
public class MoimController {

    private final MoimService moimService;

    public MoimController(final MoimService moimService) {
        this.moimService = moimService;
    }

    @PostMapping()
    public void createPubicMoim(@Login final MemberSession memberSession,
                                @RequestBody final PublicMoimAppendRequest publicMoimAppendRequest) {
        moimService.createPublicMoim(memberSession.id(), memberSession.universityId(), publicMoimAppendRequest);
    }
}
