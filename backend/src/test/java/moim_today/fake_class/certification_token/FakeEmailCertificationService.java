package moim_today.fake_class.certification_token;

import moim_today.application.certification.email.EmailCertificationService;

public class FakeEmailCertificationService implements EmailCertificationService {

    @Override
    public void sendCertificationEmail(final String email) {

    }

    @Override
    public void certifyEmail(final String certificationToken) {

    }
}
