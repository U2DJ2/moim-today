package moim_today.application.member;

import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileInfoResponse;
import moim_today.dto.member.MemberProfileResponse;
import moim_today.dto.member.PasswordRecoverRequest;
import moim_today.dto.member.PasswordUpdateRequest;
import moim_today.dto.member.ProfileUpdateRequest;
import moim_today.implement.file.FileUploader;
import moim_today.implement.member.MemberFinder;
import moim_today.implement.member.MemberUpdater;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static moim_today.global.constant.FileTypeConstant.PROFILE_IMAGE;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberUpdater memberUpdater;

    private final MemberFinder memberFinder;

    private final FileUploader fileUploader;

    public MemberServiceImpl(final MemberUpdater memberUpdater,
                             final MemberFinder memberFinder,
                             final FileUploader fileUploader) {
        this.memberUpdater = memberUpdater;
        this.memberFinder = memberFinder;
        this.fileUploader = fileUploader;
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
    public void updateProfileImage(final long memberId, final MultipartFile file) {
        FileInfoResponse fileInfoResponse = fileUploader.uploadFile(PROFILE_IMAGE.value(), file);
        memberUpdater.updateProfileImageUrl(memberId, fileInfoResponse.uploadFileUrl());
    }
}
