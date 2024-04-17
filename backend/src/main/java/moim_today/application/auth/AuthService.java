package moim_today.application.auth;

import moim_today.dto.auth.MemberLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import moim_today.dto.member.MemberRegisterRequest;

public interface AuthService {

    void login(final MemberLoginRequest memberLoginRequest, final HttpServletRequest request);

    void register(MemberRegisterRequest memberRegisterRequest);

    void logout(final HttpServletRequest request);
}
