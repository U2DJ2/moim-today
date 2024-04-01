package booki_today.application.auth;

import booki_today.dto.auth.MemberLoginRequest;
import booki_today.implement.member.MemberAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CookieSession implements AuthService{

    private final MemberAuthentication memberAuthentication;

    public CookieSession(final MemberAuthentication memberAuthentication) {
        this.memberAuthentication = memberAuthentication;
    }

    @Override
    public void login(final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        memberAuthentication.login(memberLoginRequest, request);
    }
}
