package moim_today.application.member;

import moim_today.domain.member.MemberSession;
import moim_today.dto.admin.user_inquiry.UserInquiryRequest;
import moim_today.dto.member.*;
import moim_today.implement.admin.user_inquiry.UserInquiryAppender;
import moim_today.implement.file.FileUploader;
import moim_today.implement.member.MemberComposition;
import moim_today.implement.member.MemberFinder;
import moim_today.implement.member.MemberUpdater;
import moim_today.implement.moim.moim.MoimManager;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static moim_today.global.constant.FileTypeConstant.PROFILE_IMAGE;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberComposition memberComposition;
    private final FileUploader fileUploader;
    private final MoimManager moimManager;
    private final UserInquiryAppender userInquiryAppender;

    public MemberServiceImpl(final MemberComposition memberComposition,
                             final FileUploader fileUploader,
                             final MoimManager moimManager,
                             final UserInquiryAppender userInquiryAppender) {
        this.memberComposition = memberComposition;
        this.fileUploader = fileUploader;
        this.moimManager = moimManager;
        this.userInquiryAppender = userInquiryAppender;
    }

    @Override
    public void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest) {
        memberComposition.updatePassword(memberSession.id(), passwordUpdateRequest.newPassword());
    }

    @Override
    public void recoverPassword(final PasswordRecoverRequest passwordRecoverRequest) {
        memberComposition.recoverPassword(
                passwordRecoverRequest.passwordToken(),
                passwordRecoverRequest.newPassword(),
                LocalDateTime.now()
        );
    }

    @Override
    public MemberProfileResponse getMemberProfile(final MemberSession memberSession) {
        return memberComposition.getMemberProfile(memberSession.id());
    }

    @Override
    public void updateProfile(final long memberId,
                              final ProfileUpdateRequest profileUpdateRequest) {
        memberComposition.updateProfile(memberId, profileUpdateRequest);
    }

    @Override
    public ProfileImageResponse uploadProfileImage(final long memberId, final MultipartFile file) {
        String imageUrl = fileUploader.uploadFile(PROFILE_IMAGE.value(), file);
        return ProfileImageResponse.from(imageUrl);
    }

    @Override
    public MemberHostResponse isHost(final long memberId, final long moimId) {
        boolean isHost = moimManager.isHost(memberId, moimId);
        return MemberHostResponse.from(isHost);
    }

    @Override
    public MemberJoinedMoimResponse isJoinedMoim(final long moimId, final long memberId) {
        boolean isJoinedMoim = moimManager.isJoinedMoim(moimId, memberId);
        return MemberJoinedMoimResponse.from(isJoinedMoim);
    }

    @Override
    public MemberSimpleResponse getHostProfileByMoimId(final long moimId) {
        return memberComposition.getHostProfileByMoimId(moimId);
    }

    @Override
    public void createUserInquiry(final MemberSession memberSession, final UserInquiryRequest userInquiryRequest) {
        long memberId = memberSession.id();
        long universityId = memberSession.universityId();
        long departmentId = memberSession.departmentId();

        UserInquiryJpaEntity userInquiryJpaEntity = userInquiryRequest.toEntity(memberId, universityId, departmentId);
        userInquiryAppender.createUserInquiry(userInquiryJpaEntity);
    }
}
