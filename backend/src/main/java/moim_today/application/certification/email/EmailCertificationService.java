package moim_today.application.certification.email;

import moim_today.dto.certification.CompleteEmailCertificationResponse;


public interface EmailCertificationService {

    void sendCertificationEmail(final String email);

    void certifyEmail(final String certificationToken);

    CompleteEmailCertificationResponse completeCertification(final String email);
}
