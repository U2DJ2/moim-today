package moim_today.presentation.moim;

import moim_today.application.moim.MoimService;
import moim_today.application.moim.MoimServiceImpl;
import moim_today.application.moim.moim.MoimService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.moim.*;
import moim_today.dto.moim.MoimMembersResponse;
import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.dto.moim.UploadMoimImageResponse;
import moim_today.dto.moim.moim.*;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/moims")
@RestController
public class MoimController {

    private final MoimService moimService;

    public MoimController(final MoimService moimService) {
        this.moimService = moimService;
    }

    @PostMapping
    public void createMoim(@Login final MemberSession memberSession,
                           @RequestBody final MoimAppendRequest moimAppendRequest) {
        moimService.createMoim(memberSession.id(), memberSession.universityId(), moimAppendRequest);
    }

    @PostMapping("/image")
    public MoimImageResponse uploadMoimImage(@Login final MemberSession memberSession,
                                             @RequestPart final MultipartFile file) {
        return moimService.uploadMoimImage(file);
    }

    @GetMapping("/detail")
    public MoimDetailResponse getMoimDetail(@RequestParam final long moimId) {
        return moimService.getMoimDetail(moimId);
    }

    @PatchMapping
    public void updateMoim(@Login final MemberSession memberSession,
                           @RequestBody final MoimUpdateRequest moimUpdateRequest) {
        moimService.updateMoim(memberSession.id(), moimUpdateRequest);
    }

    @GetMapping("/members")
    public MoimMembersResponse findMoimMembers(@Login final MemberSession memberSession,
                                               @RequestParam final long moimId){
        return moimService.findMoimMembers(memberSession.id(), moimId);
    }

    @DeleteMapping
    public void deleteMoim(@Login final MemberSession memberSession,
                           @RequestBody final MoimDeleteRequest moimDeleteRequest) {
        moimService.deleteMoim(memberSession.id(), moimDeleteRequest.moimId());
    }
}
