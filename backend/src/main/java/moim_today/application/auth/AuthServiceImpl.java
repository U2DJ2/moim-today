package moim_today.application.auth;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.domain.member.MemberSession;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.dto.auth.MemberSessionValidateResponse;
import moim_today.implement.department.department.DepartmentComposition;
import moim_today.implement.member.MemberComposition;
import moim_today.implement.university.UniversityComposition;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final MemberComposition memberComposition;
    private final UniversityComposition universityComposition;
    private final DepartmentComposition departmentComposition;

    public AuthServiceImpl(final MemberComposition memberComposition,
                           final UniversityComposition universityComposition,
                           final DepartmentComposition departmentComposition) {
        this.memberComposition = memberComposition;
        this.universityComposition = universityComposition;
        this.departmentComposition = departmentComposition;
    }

    @Override
    public void login(final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        memberComposition.login(memberLoginRequest, request);
    }

    @Override
    public void logout(final HttpServletRequest request) {
        memberComposition.logout(request);
    }

    @Override
    public void signUp(final MemberSignUpRequest memberSignUpRequest,
                       final HttpServletRequest request) {
        universityComposition.checkUniversityIdIsPresent(memberSignUpRequest.universityId());
        departmentComposition.validateBelongToUniversity(memberSignUpRequest.universityId(), memberSignUpRequest.departmentId());
        memberComposition.validateEmailNotExists(memberSignUpRequest.email());
        memberComposition.signUp(memberSignUpRequest, request);
    }

    @Override
    public MemberSessionValidateResponse validateMemberSession(final HttpServletRequest request) {
        return MemberSession.validateMemberSession(request);
    }
}
