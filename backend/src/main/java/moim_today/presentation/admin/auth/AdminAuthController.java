package moim_today.presentation.admin.auth;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.application.admin.auth.AdminAuthService;
import moim_today.domain.university.AdminSession;
import moim_today.dto.admin.auth.AdminLoginRequest;
import moim_today.dto.admin.auth.AdminSessionResponse;
import moim_today.dto.university.UniversityNameResponse;
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
    public void adminLogin(@RequestBody final AdminLoginRequest adminLoginRequest,
                           final HttpServletRequest request) {
        adminAuthService.login(adminLoginRequest, request);
    }

    @GetMapping("/universities")
    public AdminSessionResponse adminValidate(@Login final AdminSession adminSession) {
        return AdminSessionResponse.from(adminSession);
    }

    @PostMapping("/logout")
    public void logout(final HttpServletRequest request) {
        adminAuthService.logout(request);
    }
}
