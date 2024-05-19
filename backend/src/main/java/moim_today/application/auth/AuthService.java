package moim_today.application.auth;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.dto.auth.MemberSessionValidateResponse;

public interface AuthService {

    void login(final MemberLoginRequest memberLoginRequest, final HttpServletRequest request);

    void signUp(final MemberSignUpRequest memberSignUpRequest, final HttpServletRequest request);

    void logout(final HttpServletRequest request);

    MemberSessionValidateResponse validateMemberSession(final HttpServletRequest request);
}
