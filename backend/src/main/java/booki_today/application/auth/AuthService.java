package booki_today.application.auth;

import booki_today.dto.auth.MemberLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    void login(final MemberLoginRequest memberLoginRequest, final HttpServletRequest request);
}
