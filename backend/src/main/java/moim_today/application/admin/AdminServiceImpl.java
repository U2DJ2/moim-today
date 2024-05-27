package moim_today.application.admin;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.member.MemberResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.implement.admin.member.MemberAdmin;
import moim_today.implement.member.AuthManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private final MemberAdmin memberAdmin;
    private final AuthManager authManager;

    @Value("${admin.email}")
    private String adminEmail;

    public AdminServiceImpl(final MemberAdmin memberAdmin, final AuthManager authManager) {
        this.memberAdmin = memberAdmin;
        this.authManager = authManager;
    }

    @Override
    public List<MemberResponse> findAllMembers(final long memberId, final long universityId, final long departmentId) {
        memberAdmin.validateAdmin(adminEmail, memberId);
        return memberAdmin.findAllMembers(universityId, departmentId);
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
