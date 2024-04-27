package moim_today.presentation.moim;

import moim_today.application.moim.MoimService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.moim.*;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/moims")
@RestController
public class MoimController {

    private final MoimService moimService;

    public MoimController(final MoimService moimService) {
        this.moimService = moimService;
    }

    @PostMapping("/public")
    public void createPubicMoim(@Login final MemberSession memberSession,
                                @RequestBody final PublicMoimAppendRequest publicMoimAppendRequest) {
        moimService.createPublicMoim(memberSession.id(), memberSession.universityId(), publicMoimAppendRequest);
    }

    @PostMapping("/private")
    public void createPrivateMoim(@Login final MemberSession memberSession,
                                  @RequestBody final PrivateMoimAppendRequest privateMoimAppendRequest) {
        moimService.createPrivateMoim(memberSession.id(), memberSession.universityId(), privateMoimAppendRequest);
    }

    @PostMapping("/image")
    public UploadMoimImageResponse uploadMoimImage(@Login final MemberSession memberSession,
                                                   @RequestPart final MultipartFile file) {
        return moimService.uploadMoimImage(file);
    }

    @GetMapping("/detail")
    public MoimDetailResponse getMoimDetail(@RequestBody final MoimDetailRequest moimDetailRequest) {
        return moimService.getMoimDetail(moimDetailRequest.moimId());
    }
}
