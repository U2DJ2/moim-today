package moim_today.presentation.auth;

import moim_today.application.auth.AuthService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.auth.MemberLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public void login(@RequestBody final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        authService.login(memberLoginRequest, request);
    }

    @PostMapping("/logout")
    public void logout(@Login final MemberSession memberSession, final HttpServletRequest request) {
        authService.logout(request);
    }
}
