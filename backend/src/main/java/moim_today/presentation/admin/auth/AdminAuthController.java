package moim_today.presentation.admin;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.application.admin.AdminService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.member.MemberResponse;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public void adminLogin(@RequestBody final MemberLoginRequest loginRequest,
                           final HttpServletRequest request) {
        adminService.login(loginRequest, request);
    }

    @PostMapping("/validate")
    public boolean adminValidate(@Login final MemberSession memberSession) {
        return adminService.validateAdmin(memberSession.id());
    }

    //회원관리(조회, 삭제)
    @GetMapping("/members")
    public CollectionResponse<List<MemberResponse>> findAllMembers(
            @Login final MemberSession memberSession,
            @RequestParam final long universityId,
            @RequestParam final long departmentId) {

        List<MemberResponse> memberResponses = adminService.findAllMembers(memberSession.id(), universityId, departmentId);
        return CollectionResponse.from(memberResponses);
    }

    //모임관리(조회, 삭제)

    //미팅관리(조회, 삭제)

    //서비스 문의(조회)

    //학과 추가 문의(조회)
}
