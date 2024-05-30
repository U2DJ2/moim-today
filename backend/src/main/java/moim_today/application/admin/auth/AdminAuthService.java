package moim_today.application.admin.auth;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.dto.auth.MemberLoginRequest;


public interface AdminAuthService {


    void login(final MemberLoginRequest loginRequest, final HttpServletRequest request);

    boolean validateAdmin(final long memberId);
}
