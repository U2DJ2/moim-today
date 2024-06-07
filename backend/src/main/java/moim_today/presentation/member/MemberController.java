package moim_today.presentation.member;

import jakarta.validation.Valid;
import moim_today.application.member.MemberService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.admin.user_inquiry.UserInquiryRequest;
import moim_today.dto.member.*;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/password-recovery")
    public void recoverPassword(@RequestBody @Valid final PasswordRecoverRequest passwordRecoverRequest) {
        memberService.recoverPassword(passwordRecoverRequest);
    }

    @PatchMapping("/password")
    public void updatePassword(@Login final MemberSession memberSession,
                               @RequestBody @Valid final PasswordUpdateRequest passwordUpdateRequest) {
        memberService.updatePassword(memberSession, passwordUpdateRequest);
    }

    @GetMapping("/profile")
    public MemberProfileResponse getMemberProfile(@Login final MemberSession memberSession) {
        return memberService.getMemberProfile(memberSession);
    }

    @PatchMapping("/profile")
    public void updateProfile(@Login final MemberSession memberSession,
                              @RequestBody @Valid final ProfileUpdateRequest profileUpdateRequest) {
        memberService.updateProfile(memberSession.id(), profileUpdateRequest);
    }

    @PostMapping("/profile-image")
    public ProfileImageResponse uploadProfileImage(@Login final MemberSession memberSession,
                                                   @RequestPart final MultipartFile file) {
        return memberService.uploadProfileImage(memberSession.id(), file);
    }

    @GetMapping("/{moimId}/hosts")
    public MemberHostResponse isHost(@Login final MemberSession memberSession,
                                     @PathVariable final long moimId) {
        return memberService.isHost(memberSession.id(), moimId);
    }

    @GetMapping("/{moimId}/joining")
    public MemberJoinedMoimResponse isJoinedMoim(@Login final MemberSession memberSession,
                                                 @PathVariable final long moimId) {
        return memberService.isJoinedMoim(moimId, memberSession.id());
    }

    @GetMapping("/host-profile/{moimId}")
    public MemberSimpleResponse getHostProfileByMoimId(@PathVariable final long moimId) {
        return memberService.getHostProfileByMoimId(moimId);
    }

    @PostMapping("/user-inquiry")
    public void createUserInquiry(@Login final MemberSession memberSession,
                                  @RequestBody @Valid final UserInquiryRequest userInquiryRequest){
        memberService.createUserInquiry(memberSession, userInquiryRequest);
    }
}
