package moim_today.fake_class.certification;

import moim_today.application.certification.password.PasswordCertificationService;
import moim_today.global.error.NotFoundException;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;

public class FakePasswordCertificationService implements PasswordCertificationService {

    @Override
    public void sendPasswordToken(final String email) {
        if (!email.equals(EMAIL.value())) {
            throw new NotFoundException(EMAIL_NOT_FOUND_ERROR.message());
        }
    }
}
