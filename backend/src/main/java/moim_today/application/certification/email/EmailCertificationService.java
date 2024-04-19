package moim_today.application.certification.email;

public interface EmailCertificationService {

    void sendCertificationEmail(final String email);

    void certifyEmail(final String certificationToken);
}
