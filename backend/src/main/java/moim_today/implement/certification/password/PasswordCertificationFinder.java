package moim_today.implement.certification.password;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import moim_today.persistence.repository.certification.password.PasswordCertificationRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class PasswordCertificationFinder {

    private final PasswordCertificationRepository passwordCertificationRepository;

    public PasswordCertificationFinder(final PasswordCertificationRepository passwordCertificationRepository) {
        this.passwordCertificationRepository = passwordCertificationRepository;
    }

    @Transactional(readOnly = true)
    public PasswordCertificationJpaEntity getByCertificationToken(final String certificationToken) {
        return passwordCertificationRepository.getByCertificationToken(certificationToken);
    }
}
