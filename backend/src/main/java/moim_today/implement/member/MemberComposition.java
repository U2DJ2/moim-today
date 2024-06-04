package moim_today.implement.member;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.dto.member.MemberProfileResponse;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.dto.member.ProfileUpdateRequest;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.global.annotation.Implement;

import java.time.LocalDateTime;
import java.util.List;



@Implement
public class MemberComposition {

    private final MemberFinder memberFinder;
    private final MemberUpdater memberUpdater;
    private final AuthManager authManager;

    public MemberComposition(final MemberFinder memberFinder,
                             final MemberUpdater memberUpdater,
                             final AuthManager authManager) {
        this.memberFinder = memberFinder;
        this.memberUpdater = memberUpdater;
        this.authManager = authManager;
    }

    public void validateEmailExists(final String email) {
        memberFinder.validateEmailExists(email);
    }

    public void validateAlreadyExists(final String email) {
        memberFinder.validateAlreadyExists(email);
    }

    public MemberProfileResponse getMemberProfile(final long memberId) {
        return memberFinder.getMemberProfile(memberId);
    }

    public void validateEmailNotExists(final String email){
        memberFinder.validateEmailNotExists(email);
    }

    public List<MoimMemberResponse> findMoimMembers(final List<Long> memberIds, final long hostId,
                                                    final long moimId) {
        return memberFinder.findMoimMembers(memberIds, hostId, moimId);
    }

    public MemberSimpleResponse getHostProfileByMoimId(final long moimId) {
        return memberFinder.getHostProfileByMoimId(moimId);
    }

    public void updatePassword(final long memberId, final String newPassword) {
        memberUpdater.updatePassword(memberId, newPassword);
    }

    public void recoverPassword(final String passwordToken, final String newPassword, final LocalDateTime now) {
        memberUpdater.recoverPassword(passwordToken, newPassword, now);
    }

    public void updateProfile(final long memberId,
                              final ProfileUpdateRequest profileUpdateRequest) {
        memberUpdater.updateProfile(memberId, profileUpdateRequest);
    }

    public void login(final MemberLoginRequest memberLoginRequest,
                      final HttpServletRequest request) {
        authManager.login(memberLoginRequest, request);
    }

    public void logout(final HttpServletRequest request) {
        authManager.logout(request);
    }

    public void signUp(final MemberSignUpRequest memberSignUpRequest, final HttpServletRequest request) {
        authManager.signUp(memberSignUpRequest, request);
    }
}
