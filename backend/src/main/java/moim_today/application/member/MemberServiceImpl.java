package moim_today.application.member;

import moim_today.domain.member.MemberSession;
import moim_today.dto.member.*;
import moim_today.implement.file.FileUploader;
import moim_today.implement.member.MemberFinder;
import moim_today.implement.member.MemberUpdater;
import moim_today.implement.moim.moim.MoimManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static moim_today.global.constant.FileTypeConstant.PROFILE_IMAGE;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberUpdater memberUpdater;
    private final MemberFinder memberFinder;
    private final FileUploader fileUploader;
    private final MoimManager moimManager;

    public MemberServiceImpl(final MemberUpdater memberUpdater,
                             final MemberFinder memberFinder,
                             final FileUploader fileUploader,
                             final MoimManager moimManager) {
        this.memberUpdater = memberUpdater;
        this.memberFinder = memberFinder;
        this.fileUploader = fileUploader;
        this.moimManager = moimManager;
    }

    @Override
    public void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest) {
        memberUpdater.updatePassword(memberSession.id(), passwordUpdateRequest.newPassword());
    }

    @Override
    public void recoverPassword(final PasswordRecoverRequest passwordRecoverRequest) {
        memberUpdater.recoverPassword(
                passwordRecoverRequest.passwordToken(),
                passwordRecoverRequest.newPassword(),
                LocalDateTime.now()
        );
    }

    @Override
    public MemberProfileResponse getMemberProfile(final MemberSession memberSession) {
        return memberFinder.getMemberProfile(memberSession.id());
    }

    @Override
    public void updateProfile(final long memberId,
                              final long universityId,
                              final ProfileUpdateRequest profileUpdateRequest) {
        memberUpdater.updateProfile(memberId, universityId, profileUpdateRequest);
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
        return memberFinder.getHostProfileByMoimId(moimId);
    }
}
