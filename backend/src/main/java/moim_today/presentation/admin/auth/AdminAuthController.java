package moim_today.presentation.admin.auth;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.application.admin.auth.AdminAuthService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/admin")
@RestController
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(final AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public void adminLogin(@RequestBody final MemberLoginRequest loginRequest,
                           final HttpServletRequest request) {
        adminAuthService.login(loginRequest, request);
    }

    @PostMapping("/validate")
    public boolean adminValidate(@Login final MemberSession memberSession) {
        return adminAuthService.validateAdmin(memberSession.id());
    }

    //모임관리(조회, 삭제)

    //미팅관리(조회, 삭제)

    //서비스 문의(조회)

    //학과 추가 문의(조회)
}
