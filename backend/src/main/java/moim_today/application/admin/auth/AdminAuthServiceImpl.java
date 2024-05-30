package moim_today.application.admin.auth;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.implement.admin.member.MemberAdmin;
import moim_today.implement.member.AuthManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    private final MemberAdmin memberAdmin;
    private final AuthManager authManager;

    @Value("${admin.email}")
    private String adminEmail;

    public AdminAuthServiceImpl(final MemberAdmin memberAdmin, final AuthManager authManager) {
        this.memberAdmin = memberAdmin;
        this.authManager = authManager;
    }

    @Override
    public void login(final MemberLoginRequest loginRequest, final HttpServletRequest request) {
        memberAdmin.validateAdmin(adminEmail, loginRequest.email());
        authManager.login(loginRequest, request);
    }

    @Override
    public boolean validateAdmin(final long memberId) {
        try {
            memberAdmin.validateAdmin(adminEmail, memberId);
            return true;
        } catch (final ForbiddenException e) {
            return false;
        }
    }
}
