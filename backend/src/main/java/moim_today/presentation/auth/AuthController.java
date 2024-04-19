package moim_today.presentation.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import moim_today.application.auth.AuthService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberRegisterRequest;
import moim_today.global.annotation.Login;
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

    @PostMapping("/login")
    public void login(@RequestBody final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        authService.login(memberLoginRequest, request);
    }

    @PostMapping("/logout")
    public void logout(@Login final MemberSession memberSession, final HttpServletRequest request) {
        authService.logout(request);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody final MemberRegisterRequest memberRegisterRequest,
                         final HttpServletRequest request){
        authService.register(memberRegisterRequest, request);
    }
}
