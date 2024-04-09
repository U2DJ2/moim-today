package moim_today.fake_class.certification_token;

import moim_today.application.certification_token.CertificationTokenService;
import moim_today.global.error.NotFoundException;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;

public class FakeCertificationTokenService implements CertificationTokenService {

    @Override
    public void createPasswordToken(final String email) {
        if (!email.equals(EMAIL.value())) {
            throw new NotFoundException(EMAIL_NOT_FOUND_ERROR.message());
        }
    }
}
