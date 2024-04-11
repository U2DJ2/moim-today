package moim_today.presentation.auth;

import moim_today.application.auth.AuthService;
import moim_today.dto.auth.MemberLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public void login(@RequestBody final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        authService.login(memberLoginRequest, request);
    }
}
