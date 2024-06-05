package moim_today.implement.certification.password;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;

import java.time.LocalDateTime;


@Implement
public class PasswordCertificationComposition {

    private final PasswordCertificationAppender passwordCertificationAppender;
    private final PasswordCertificationFinder passwordCertificationFinder;

    public PasswordCertificationComposition(final PasswordCertificationAppender passwordCertificationAppender,
                                            final PasswordCertificationFinder passwordCertificationFinder) {
        this.passwordCertificationAppender = passwordCertificationAppender;
        this.passwordCertificationFinder = passwordCertificationFinder;
    }

    public String createPasswordToken(final String email, final LocalDateTime expiredDateTime) {
        return passwordCertificationAppender.createPasswordToken(email, expiredDateTime);
    }

    public PasswordCertificationJpaEntity getByCertificationToken(final String certificationToken) {
        return passwordCertificationFinder.getByCertificationToken(certificationToken);
    }
}
