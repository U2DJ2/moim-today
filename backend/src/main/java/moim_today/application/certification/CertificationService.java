package moim_today.application.certification;

public interface CertificationService {

    void createPasswordToken(final String email);

    void certifyEmail(final String email);
}
