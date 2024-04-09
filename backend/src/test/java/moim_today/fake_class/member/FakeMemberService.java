package moim_today.fake_class.member;

import moim_today.application.member.MemberService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.member.PasswordRecoverRequest;
import moim_today.dto.member.PasswordUpdateRequest;
import moim_today.global.error.NotFoundException;
import moim_today.util.TestConstant;

import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR;
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
}