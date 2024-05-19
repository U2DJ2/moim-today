package moim_today.presentation.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import moim_today.application.auth.AuthService;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.dto.auth.MemberSessionValidateResponse;
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
    public void logout(final HttpServletRequest request) {
        authService.logout(request);
    }

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody final MemberSignUpRequest memberSignUpRequest,
                       final HttpServletRequest request) {
        authService.signUp(memberSignUpRequest, request);
    }

    @GetMapping("/session-validation")
    public MemberSessionValidateResponse validateMemberSession(final HttpServletRequest SessionValidateResponse) {
        return authService.validateMemberSession(SessionValidateResponse);
    }
}
