package moim_today.application.auth;

import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.member.MemberRegisterRequest;
import moim_today.implement.department.DepartmentFinder;
import moim_today.implement.member.AuthManager;
import jakarta.servlet.http.HttpServletRequest;
import moim_today.implement.member.MemberFinder;
import moim_today.implement.university.UniversityFinder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthManager authManager;
    private final MemberFinder memberFinder;
    private final UniversityFinder universityFinder;
    private final DepartmentFinder departmentFinder;

    public AuthServiceImpl(final AuthManager authManager,
                           final MemberFinder memberFinder,
                           final UniversityFinder universityFinder,
                           final DepartmentFinder departmentFinder) {
        this.authManager = authManager;
        this.memberFinder = memberFinder;
        this.universityFinder = universityFinder;
        this.departmentFinder = departmentFinder;
    }

    @Override
    public void login(final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        authManager.login(memberLoginRequest, request);
    }

    @Override
    public void logout(final HttpServletRequest request) {
        authManager.logout(request);
    }

    @Override
    public void register(final MemberRegisterRequest memberRegisterRequest) {
        universityFinder.validateUniversityId(memberRegisterRequest.universityId());
        departmentFinder.validateDepartmentId(memberRegisterRequest.universityId(), memberRegisterRequest.departmentId());
        memberFinder.checkEmailAlreadyUsed(memberRegisterRequest.email());
        authManager.register(memberRegisterRequest);
    }
}
