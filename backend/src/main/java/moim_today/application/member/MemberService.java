package moim_today.application.member;

import moim_today.domain.member.MemberSession;
import moim_today.dto.admin.user_inquiry.UserInquiryRequest;
import moim_today.dto.member.*;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest);

    void recoverPassword(final PasswordRecoverRequest passwordRecoverRequest);

    MemberProfileResponse getMemberProfile(final MemberSession memberSession);

    void updateProfile(final long memberId, final ProfileUpdateRequest profileUpdateRequest);

    ProfileImageResponse uploadProfileImage(final long memberId, final MultipartFile file);

    MemberHostResponse isHost(final long memberId, final long moimId);

    MemberJoinedMoimResponse isJoinedMoim(final long moimId, final long memberId);

    MemberSimpleResponse getHostProfileByMoimId(final long moimId);

    void createUserInquiry(final MemberSession memberSession, final UserInquiryRequest userInquiryRequest);
}
