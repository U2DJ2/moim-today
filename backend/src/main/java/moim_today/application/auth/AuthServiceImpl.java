package moim_today.application.auth;

import moim_today.dto.auth.MemberLoginRequest;
import moim_today.implement.member.AuthManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthManager authManager;

    public AuthServiceImpl(final AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void login(final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        authManager.login(memberLoginRequest, request);
    }
}
