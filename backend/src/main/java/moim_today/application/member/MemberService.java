package moim_today.application.member;

import moim_today.domain.member.MemberSession;
import moim_today.dto.member.MemberProfileResponse;
import moim_today.dto.member.PasswordRecoverRequest;
import moim_today.dto.member.PasswordUpdateRequest;
import moim_today.dto.member.ProfileUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest);

    void recoverPassword(final PasswordRecoverRequest passwordRecoverRequest);

    MemberProfileResponse getMemberProfile(final MemberSession memberSession);

    void updateProfile(final long memberId, final long universityId, final ProfileUpdateRequest profileUpdateRequest);

    void updateProfileImage(final long memberId, final MultipartFile file);
}
