package moim_today.presentation.admin.member;

import moim_today.application.admin.member.AdminMemberService;
import moim_today.domain.member.MemberSession;
import moim_today.domain.university.AdminSession;
import moim_today.dto.member.MemberResponse;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    public AdminMemberController(final AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    @GetMapping("/members")
    public CollectionResponse<List<MemberResponse>> findAllMembers(
            @Login final AdminSession adminSession,
            @RequestParam final long departmentId) {

        List<MemberResponse> memberResponses =
                adminMemberService.findAllMembers(adminSession.universityId(), departmentId);
        return CollectionResponse.from(memberResponses);
    }

    @DeleteMapping("/members/{memberId}")
    public void deleteMember(@Login final AdminSession adminSession, @PathVariable final long memberId) {
        adminMemberService.deleteMember(adminSession.universityId(), memberId);
    }
}
