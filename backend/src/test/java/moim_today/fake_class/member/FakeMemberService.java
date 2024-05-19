package moim_today.fake_class.member;

import moim_today.application.member.MemberService;
import moim_today.domain.member.MemberSession;
import moim_today.domain.member.enums.Gender;
import moim_today.dto.member.*;
import moim_today.fake_DB.FakeMemberSession;
import moim_today.global.error.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.MemberExceptionConstant.MEMBER_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;


public class FakeMemberService implements MemberService {

    @Override
    public void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest) {

    }

    @Override
    public void recoverPassword(final PasswordRecoverRequest passwordRecoverRequest) {
        if (!passwordRecoverRequest.passwordToken().equals(CERTIFICATION_TOKEN.value())) {
            throw new NotFoundException(MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR.message());
        }
    }

    @Override
    public MemberProfileResponse getMemberProfile(final MemberSession memberSession) {
        if (memberSession.id() != FakeMemberSession.createMemberSession().id()) {
            throw new NotFoundException(MEMBER_NOT_FOUND_ERROR.message());
        }

        return MemberProfileResponse.builder()
                .universityName("universityName")
                .departmentName("departmentName")
                .email(EMAIL.value())
                .username("username")
                .studentId("studenId")
                .birthDate(LocalDate.of(2000, 12, 18))
                .gender(Gender.MALE)
                .memberProfileImageUrl("testUrl")
                .build();
    }

    @Override
    public void updateProfile(final long memberId, final long universityId, final ProfileUpdateRequest profileUpdateRequest) {

    }

    @Override
    public ProfileImageResponse uploadProfileImage(final long memberId, final MultipartFile file) {
        return ProfileImageResponse.from(PROFILE_IMAGE_URL.value());
    }

    @Override
    public MemberHostResponse isHost(final long memberId, final long moimId) {
        boolean isHost = (memberId == moimId);
        return MemberHostResponse.builder()
                .isHost(isHost)
                .build();
    }

    @Override
    public MemberJoinedMoimResponse isJoinedMoim(final long moimId, final long memberId) {
        boolean isJoinedMoim = true;
        return MemberJoinedMoimResponse.from(isJoinedMoim);
    }

    @Override
    public MemberSimpleResponse getHostProfileByMoimId(final long moimId) {
        return MemberSimpleResponse.builder()
                .memberId(MEMBER_ID.longValue())
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();
    }
}
