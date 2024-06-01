package moim_today.presentation.admin.member;

import moim_today.application.admin.member.AdminMemberService;
import moim_today.domain.member.MemberSession;
import moim_today.domain.university.AdminSession;
import moim_today.dto.member.MemberResponse;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    public AdminMemberController(final AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    //회원관리(조회, 삭제)
    @GetMapping("/members")
    public CollectionResponse<List<MemberResponse>> findAllMembers(
            @Login final AdminSession adminSession,
            @RequestParam final long departmentId) {

        List<MemberResponse> memberResponses =
                adminMemberService.findAllMembers(adminSession.universityId(), departmentId);
        return CollectionResponse.from(memberResponses);
    }
}
