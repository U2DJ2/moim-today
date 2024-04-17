package moim_today.application.auth;

import moim_today.dto.auth.MemberLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    void login(final MemberLoginRequest memberLoginRequest, final HttpServletRequest request);

    void logout(final HttpServletRequest request);
}
