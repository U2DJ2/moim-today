package moim_today.fake_class.certification_token;

import moim_today.application.certification.CertificationService;
import moim_today.global.error.NotFoundException;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;

public class FakeCertificationService implements CertificationService {

    @Override
    public void sendPasswordToken(final String email) {
        if (!email.equals(EMAIL.value())) {
            throw new NotFoundException(EMAIL_NOT_FOUND_ERROR.message());
        }
    }

    @Override
    public void sendCertificationEmail(final String email) {

    }

    @Override
    public void certifyEmail(final String email) {

    }
}
