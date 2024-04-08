package moim_today.presentation.member;

import moim_today.application.member.MemberService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.member.PasswordUpdateRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PatchMapping("/password")
    public void updatePassword(@Login final MemberSession memberSession,
                               @RequestBody final PasswordUpdateRequest passwordUpdateRequest) {
        memberService.updatePassword(memberSession, passwordUpdateRequest);
    }
}
