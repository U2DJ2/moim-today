package moim_today.application.certification;

public interface CertificationService {

    void sendPasswordToken(final String email);

    void sendCertificationEmail(final String email);

    void certifyEmail(final String certificationToken);
}
