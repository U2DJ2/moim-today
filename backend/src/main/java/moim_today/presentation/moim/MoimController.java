package moim_today.presentation.moim;

import moim_today.application.moim.moim.MoimService;
import moim_today.application.moim.moim_notice.MoimNoticeService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.moim.moim.*;
import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/moims")
@RestController
public class MoimController {

    private final MoimService moimService;
    private final MoimNoticeService moimNoticeService;

    public MoimController(final MoimService moimService,
                          final MoimNoticeService moimNoticeService) {
        this.moimService = moimService;
        this.moimNoticeService = moimNoticeService;
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
    public MoimMemberTabResponse findMoimMembers(@Login final MemberSession memberSession,
                                               @RequestParam final long moimId){
        return moimService.findMoimMembers(memberSession.id(), moimId);
    }

    @DeleteMapping
    public void deleteMoim(@Login final MemberSession memberSession,
                           @RequestBody final MoimDeleteRequest moimDeleteRequest) {
        moimService.deleteMoim(memberSession.id(), moimDeleteRequest.moimId());
    }

    @PostMapping("/notices")
    public void createMoimNotice(@Login final MemberSession memberSession,
                                 @RequestBody final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeService.createMoimNotice(memberSession.id(), moimNoticeCreateRequest);
    }

    @DeleteMapping("/members")
    public void deleteMember(@Login final MemberSession memberSession,
                             @RequestBody final MoimMemberDeleteRequest moimMemberDeleteRequest){
        moimService.deleteMember(memberSession.id(), moimMemberDeleteRequest);
    }
}
