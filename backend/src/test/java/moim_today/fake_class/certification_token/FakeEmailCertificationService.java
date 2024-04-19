package moim_today.fake_class.certification_token;

import moim_today.application.certification.email.EmailCertificationService;
import moim_today.global.error.BadRequestException;
import moim_today.util.TestConstant;

import static moim_today.global.constant.exception.MemberExceptionConstant.ALREADY_EXIST_EMAIL_ERROR;

public class FakeEmailCertificationService implements EmailCertificationService {

    @Override
    public void sendCertificationEmail(final String email) {
        // 이미 가입된 이메일로 인증할 수 없음
        if (!email.equals(TestConstant.EMAIL.value())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL_ERROR.message());
        }
    }

    @Override
    public void certifyEmail(final String certificationToken) {

    }
}
